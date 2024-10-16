package com.fiap.parquimetro.service;

import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.repository.MotoristaRepository;
import com.fiap.parquimetro.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Resumo:
 * A classe MotoristaService gerencia as operações relacionadas aos motoristas, incluindo registro,
 * atualização, busca, exclusão e vínculo de veículos aos motoristas. Ela também verifica a existência
 * de motoristas por email e oferece respostas apropriadas para essas operações.
 */
@Service
@AllArgsConstructor
@Slf4j
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;

    public Motorista registrarMotorista(Motorista motorista) {
        // Verificar se já existe um motorista com o mesmo email
        if (motoristaRepository.existsByEmail(motorista.getEmail())) {
            throw new DataIntegrityViolationException("Já existe um motorista com o mesmo email: " + motorista.getEmail());
        }
        return motoristaRepository.save(motorista);
    }

    public Motorista atualizarMotorista(Motorista motorista) {
        return motoristaRepository.save(motorista);
    }

    public Optional<Motorista> obterMotoristaPorId(String motoristaId) {
        return motoristaRepository.findById(motoristaId);
    }

    public ResponseEntity<List<Veiculo>> obterVeiculosDoMotorista(String motoristaId) {
        log.info("Consultando Veiculos do Motorista{}...", motoristaId);
        Optional<Motorista> motorista = obterMotoristaPorId(motoristaId);
        if (motorista.isPresent()) {
            return new ResponseEntity<>(motorista.get().getVeiculos(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public List<Motorista> listarTodosOsMotoristas() {
        return motoristaRepository.findAll();
    }

    public void deletarTodosOsMotoristas() {
        motoristaRepository.deleteAll();
    }

    public ResponseEntity<String> vincularVeiculoAoMotorista(String motoristaId, String veiculoId) {
        log.info("Vinculando Veiculo ao Motorista{}...", motoristaId);
        Optional<Motorista> motoristaOptional = motoristaRepository.findById(motoristaId);
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(veiculoId);
        if (motoristaOptional.isPresent() && veiculoOptional.isPresent()) {
            Motorista motorista = motoristaOptional.get();
            Veiculo veiculo = veiculoOptional.get();
            // Verifique se a lista de veículos não é nula e inicialize-a se for.
            if (motorista.getVeiculos() == null) {
                motorista.setVeiculos(new ArrayList<>());
            }
            motorista.getVeiculos().add(veiculo);
            motoristaRepository.save(motorista);
            return new ResponseEntity<>("Veículo vinculado ao motorista com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Motorista ou Veículo não encontrado.", HttpStatus.NOT_FOUND);
        }
    }
}
