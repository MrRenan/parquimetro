package com.fiap.parquimetro.controller;

import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.service.MotoristaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indica que essa classe é um controlador REST
@RequestMapping("/motoristas") // Define a rota base para este controlador
@AllArgsConstructor // Lombok para gerar um construtor com todos os campos finais
public class MotoristaController {

    private final MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<Motorista> registrarMotorista(@RequestBody Motorista motorista) {
        // Registra um novo motorista baseado nos dados recebidos
        Motorista novoMotorista = motoristaService.registrarMotorista(motorista);
        // Retorna uma resposta com status 201 (CREATED) e o objeto criado
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMotorista);
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listarTodosOsMotoristas() {
        // Busca todos os motoristas
        List<Motorista> motoristas = motoristaService.listarTodosOsMotoristas();
        // Retorna a lista de motoristas com status 200 (OK)
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/{motoristaId}")
    public ResponseEntity<Motorista> obterMotorista(@PathVariable String motoristaId) {
        // Busca o motorista pelo ID
        Optional<Motorista> motorista = motoristaService.obterMotoristaPorId(motoristaId);
        // Retorna o motorista se encontrado, ou um status 404 (NOT FOUND) se não
        return motorista.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/{motoristaId}/veiculos")
    public ResponseEntity<String> vincularVeiculoAoMotorista(@PathVariable String motoristaId,
                                                             @RequestParam String veiculoId) {
        // Vincula um veículo ao motorista com base nos IDs recebidos
        return motoristaService.vincularVeiculoAoMotorista(motoristaId, veiculoId);
    }

    @GetMapping("/{motoristaId}/veiculos")
    public ResponseEntity<List<Veiculo>> obterVeiculosDoMotorista(@PathVariable String motoristaId) {
        // Busca os veículos vinculados ao motorista
        return motoristaService.obterVeiculosDoMotorista(motoristaId);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> deletarTodosOsMotoristas() {
        // Exclui todos os motoristas
        motoristaService.deletarTodosOsMotoristas();
        // Retorna uma resposta com status 204 (NO CONTENT)
        return ResponseEntity.noContent().build();
    }
}
