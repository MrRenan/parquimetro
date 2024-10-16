package com.fiap.parquimetro.controller;

import com.fiap.parquimetro.dto.VeiculoDTO;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.service.VeiculoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor // Lombok para gerar um construtor com todos os campos finais
@RestController // Indica que essa classe é um controlador REST
@RequestMapping("/veiculos") // Define a rota base para este controlador
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Veiculo> registrarVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        // Registra um novo veículo baseado nos dados recebidos
        Veiculo novoVeiculo = veiculoService.registrarVeiculo(veiculoDTO);
        // Retorna uma resposta com status 201 (CREATED) e o objeto criado
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVeiculo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarTodosOsVeiculos() {
        // Busca todos os veículos
        List<VeiculoDTO> veiculos = veiculoService.listarTodosOsVeiculos();
        // Retorna a lista de veículos com status 200 (OK)
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoDTO> obterVeiculo(@PathVariable String veiculoId) {
        // Busca o veículo pelo ID
        Optional<VeiculoDTO> veiculo = veiculoService.obterVeiculoPorId(veiculoId);
        // Retorna o veículo se encontrado, ou um status 404 (NOT FOUND) se não
        return veiculo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculoPorId(@PathVariable String id) {
        // Exclui todos os veículos
        veiculoService.deletarVeiculoPorId(id);
        // Retorna uma resposta com status 204 (NO CONTENT)
        return ResponseEntity.noContent().build();
    }
}
