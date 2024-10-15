package com.fiap.parquimetro.service;

import com.fiap.parquimetro.dto.PagamentoDTO;
import com.fiap.parquimetro.mapper.PagamentoMapper;
import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Pagamento;
import com.fiap.parquimetro.repository.MotoristaRepository;
import com.fiap.parquimetro.repository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Resumo:
 * A classe PagamentoService gerencia as operações relacionadas aos pagamentos, incluindo criação,
 * busca e exclusão. Ela mapeia entidades de pagamento para DTOs e vice-versa, além de garantir que
 * os pagamentos estejam associados aos motoristas corretos.
 */
@Service
@AllArgsConstructor
public class PagamentoService {

    private final MotoristaRepository motoristaRepository;
    private final PagamentoRepository formaPagamentoRepository;
    private final PagamentoMapper formaPagamentoMapper;

    public PagamentoDTO criarPagamento(PagamentoDTO formaPagamentoDTO) {
        String motoristaId = formaPagamentoDTO.getMotoristaId();
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(motoristaId);
        if (motoristaOpt.isEmpty()) {
            throw new DataIntegrityViolationException("Motorista com ID não encontrado: " + motoristaId);
        }

        Pagamento novaPagamento = formaPagamentoRepository.save(formaPagamentoMapper.toEntity(formaPagamentoDTO));
        Motorista motorista = motoristaOpt.get();
        if (motorista.getPagamentos() == null) {
            motorista.setPagamentos(List.of(novaPagamento));
        } else {
            motorista.getPagamentos().add(novaPagamento);
        }
        motoristaRepository.save(motorista);
        return formaPagamentoMapper.toDTO(novaPagamento);
    }

    public Optional<Pagamento> obterPagamento(String id) {
        return formaPagamentoRepository.findById(id);
    }

    public List<PagamentoDTO> findAll() {
        return formaPagamentoRepository.findAll()
                .stream()
                .map(formaPagamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        formaPagamentoRepository.deleteAll();
    }
}
