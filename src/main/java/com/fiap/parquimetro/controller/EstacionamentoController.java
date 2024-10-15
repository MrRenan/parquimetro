package com.fiap.parquimetro.controller;

import com.fiap.parquimetro.dto.AtualizarSaidaEstacionamentoDTO;
import com.fiap.parquimetro.dto.IniciarEstacionamentoDTO;
import com.fiap.parquimetro.model.Estacionamento;
import com.fiap.parquimetro.repository.EstacionamentoRepository;
import com.fiap.parquimetro.service.EstacionamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indica que essa classe é um controlador REST
@RequestMapping("/estacionamentos") // Define a rota base para este controlador
@AllArgsConstructor // Lombok para gerar um construtor com todos os campos finais
public class EstacionamentoController {

    private final EstacionamentoRepository estacionamentoRepository;
    private final EstacionamentoService estacionamentoService;

    @PostMapping
    public ResponseEntity<Estacionamento> criarEstacionamento(@RequestBody IniciarEstacionamentoDTO iniciarEstacionamentoDTO) {
        // Cria um novo estacionamento baseado nos dados recebidos
        Estacionamento novoEstacionamento = estacionamentoService.criarEstacionamento(iniciarEstacionamentoDTO);
        // Retorna uma resposta com status 201 (CREATED) e o objeto criado
        return new ResponseEntity<>(novoEstacionamento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estacionamento> obterEstacionamento(@PathVariable String id) {
        // Busca o estacionamento pelo ID
        Optional<Estacionamento> estacionamentoOptional = estacionamentoRepository.findById(id);
        // Retorna o estacionamento se encontrado, ou um status 404 (NOT FOUND) se não
        return estacionamentoOptional
                .map(estacionamento -> new ResponseEntity<>(estacionamento, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Estacionamento>> listarEstacionamentos() {
        // Busca todos os estacionamentos
        List<Estacionamento> estacionamentos = estacionamentoRepository.findAll();
        // Retorna a lista de estacionamentos com status 200 (OK)
        return new ResponseEntity<>(estacionamentos, HttpStatus.OK);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirEstacionamentos() {
        // Exclui todos os estacionamentos
        estacionamentoRepository.deleteAll();
        // Retorna uma resposta com status 204 (NO CONTENT)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estacionamento> atualizarSaidaEstacionamento(
            @PathVariable String id,
            @RequestBody AtualizarSaidaEstacionamentoDTO atualizarSaidaDTO
    ) {
        // Atualiza a saída do estacionamento com base nos dados recebidos
        return new ResponseEntity<>(
                estacionamentoService.atualizarSaidaEstacionamento(id, atualizarSaidaDTO), HttpStatus.OK);
    }
}
