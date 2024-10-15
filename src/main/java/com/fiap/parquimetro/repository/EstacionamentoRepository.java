package com.fiap.parquimetro.repository;

import com.fiap.parquimetro.model.Estacionamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EstacionamentoRepository extends MongoRepository<Estacionamento, String> {

    // Busca todos os estacionamentos onde a saída é nula e a entrada é menor ou igual ao horário fornecido
    List<Estacionamento> findAllBySaidaIsNullAndEntradaLessThanEqual(LocalDateTime entrada);
}
