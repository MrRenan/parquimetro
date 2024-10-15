package com.fiap.parquimetro.service;

import com.fiap.parquimetro.dto.AtualizarSaidaEstacionamentoDTO;
import com.fiap.parquimetro.dto.IniciarEstacionamentoDTO;
import com.fiap.parquimetro.enums.TipoPeriodoEstacionamento;
import com.fiap.parquimetro.mapper.EstacionamentoMapper;
import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Estacionamento;
import com.fiap.parquimetro.model.Pagamento;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.repository.MotoristaRepository;
import com.fiap.parquimetro.repository.EstacionamentoRepository;
import com.fiap.parquimetro.repository.PagamentoRepository;
import com.fiap.parquimetro.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Resumo:
 * A classe EstacionamentoService gerencia as operações de criação, atualização e cálculo de valor dos
 * estacionamentos. Ela também verifica estacionamentos com tempo expirando e envia mensagens usando o
 * serviço SQS.
 */
@Service
@AllArgsConstructor
@Slf4j
public class EstacionamentoService {

    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final EstacionamentoRepository estacionamentoRepository;
    private final EstacionamentoMapper estacionamentoMapper;
    private final SQSService sqsService;
    private final ObjectMapper objectMapper;
    private final PagamentoRepository formaPagamentoRepository;

    public Estacionamento criarEstacionamento(IniciarEstacionamentoDTO iniciarEstacionamentoDTO) {
        // Verifica a existência do motorista
        String motoristaId = iniciarEstacionamentoDTO.getMotoristaId();
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(motoristaId);
        if (motoristaOpt.isEmpty()) {
            throw new DataIntegrityViolationException("Motorista com ID não encontrado: " + motoristaId);
        }

        // Verifica a existência do veículo
        String veiculoId = iniciarEstacionamentoDTO.getVeiculoId();
        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(veiculoId);
        if (veiculoOpt.isEmpty()) {
            throw new DataIntegrityViolationException("Veículo com ID não encontrado: " + veiculoId);
        }

        // Verifica se o veículo está associado ao motorista
        if (motoristaOpt.get().getVeiculos().stream().noneMatch(veiculo -> veiculo.getId().equals(veiculoId))) {
            String mensagemDeErro = "O veículo não está associado ao motorista. Veículo ID: " + veiculoId + ", Motorista ID: " + motoristaId;
            throw new DataIntegrityViolationException(mensagemDeErro);
        }

        Estacionamento estacionamento = estacionamentoMapper.toEntity(iniciarEstacionamentoDTO);

        if (iniciarEstacionamentoDTO.getTipo() == TipoPeriodoEstacionamento.PERIODO_FIXO) {
            // Informar saída é obrigatório
            if (iniciarEstacionamentoDTO.getSaida() == null) {
                throw new DataIntegrityViolationException("Informar a saída é obrigatório para tempo fixo.");
            }
            // Calcular valor para estacionamento de tempo fixo
            //calcularValor(estacionamento);
        } else {
            // Valor será calculado quando usuário atualizar via API PUT o horário de saída do estacionamento
            if (iniciarEstacionamentoDTO.getSaida() != null) {
                throw new DataIntegrityViolationException("Saída só pode ser informada quando tipo de estacionamento é PERIODO_FIXO");
            }
        }

        estacionamento.setMotorista(motoristaOpt.get());
        estacionamento.setVeiculo(veiculoOpt.get());
        Estacionamento novoEstacionamento = estacionamentoRepository.save(estacionamento);

        try {
            String estacionamentoJSON = objectMapper.writeValueAsString(estacionamento);
            sqsService.sendMessage(estacionamentoJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return novoEstacionamento;
    }

    public List<Estacionamento> encontrarEstacionamentosComTempoExpirando() {
        LocalDateTime tempoMinutosAntes = LocalDateTime.now().minusMinutes(5L);
        return estacionamentoRepository.findAllBySaidaIsNullAndEntradaLessThanEqual(tempoMinutosAntes);
    }

    public Estacionamento atualizarSaidaEstacionamento(String id, AtualizarSaidaEstacionamentoDTO atualizarSaidaDTO) {
        Estacionamento estacionamento = estacionamentoRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException("Estacionamento não encontrado com o ID: " + id));

        if (estacionamento.getTipo() != TipoPeriodoEstacionamento.POR_HORA) {
            throw new DataIntegrityViolationException("Somente estacionamentos do tipo POR_HORA podem ter o horário de saída atualizado.");
        }

        if (estacionamento.getMotorista().getPagamentos()
                .stream()
                .map(Pagamento::getId)
                .noneMatch(formaPagamentoId -> formaPagamentoId.equals(atualizarSaidaDTO.getPagamentoId()))) {
            throw new DataIntegrityViolationException("Forma de pagamento não encontrada para o motorista.");
        }

        estacionamento.setSaida(atualizarSaidaDTO.getSaida());
        Pagamento formaPagamento = formaPagamentoRepository.findById(atualizarSaidaDTO.getPagamentoId())
                .orElseThrow(() -> new DataIntegrityViolationException("Forma de pagamento não encontrada com o ID: " + atualizarSaidaDTO.getPagamentoId()));
        estacionamento.setPagamento(formaPagamento);
        calcularValor(estacionamento);

        try {
            String estacionamentoJSON = objectMapper.writeValueAsString(estacionamento);
            sqsService.sendMessage(estacionamentoJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estacionamentoRepository.save(estacionamento);
    }

    public void calcularValor(Estacionamento estacionamento) {
        if (estacionamento.getEntrada() != null && estacionamento.getSaida() != null) {
            long minutosEstacionado = Duration.between(estacionamento.getEntrada(), estacionamento.getSaida()).toMinutes();
            double horasEstacionado = minutosEstacionado / 60.0;
            int horasCheias = (int) Math.ceil(horasEstacionado);
            estacionamento.setValor(horasCheias * 10);
        }
    }
}
