package com.fiap.parquimetro.controller;

import com.fiap.parquimetro.dto.PagamentoDTO;
import com.fiap.parquimetro.model.Pagamento;
import com.fiap.parquimetro.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController // Indica que essa classe é um controlador REST
@RequestMapping("/formas-pagamento") // Define a rota base para este controlador
public class PagamentoController {

    private final PagamentoService formaPagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO formaPagamentoDTO) {
        // Cria um novo pagamento baseado nos dados recebidos
        PagamentoDTO novoPagamento = formaPagamentoService.criarPagamento(formaPagamentoDTO);
        // Retorna uma resposta com status 201 (CREATED) e o objeto criado
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> obterPagamento(@PathVariable String id) {
        // Busca o pagamento pelo ID
        Optional<Pagamento> formaPagamentoOptional = formaPagamentoService.obterPagamento(id);
        // Retorna o pagamento se encontrado, ou um status 404 (NOT FOUND) se não
        return formaPagamentoOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarPagamentos() {
        // Busca todas as formas de pagamento
        List<PagamentoDTO> formasPagamento = formaPagamentoService.findAll();
        // Retorna a lista de formas de pagamento com status 200 (OK)
        return ResponseEntity.ok(formasPagamento);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> deletarTodasFormasPagamento() {
        // Exclui todas as formas de pagamento
        formaPagamentoService.deleteAll();
        // Retorna uma resposta com status 204 (NO CONTENT)
        return ResponseEntity.noContent().build();
    }
}
