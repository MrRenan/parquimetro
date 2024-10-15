package com.fiap.parquimetro.repository;

import com.fiap.parquimetro.model.Motorista;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um repositório Spring
public interface MotoristaRepository extends MongoRepository<Motorista, String> {

    // Verifica se um motorista já existe pelo email
    boolean existsByEmail(String email);
}
