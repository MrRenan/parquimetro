package com.fiap.parquimetro.repository;

import com.fiap.parquimetro.model.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagamentoRepository extends MongoRepository<Pagamento, String> {
    // Interface para realizar operações CRUD com a coleção Pagamento no MongoDB
}
