package com.fiap.parquimetro.repository;

import com.fiap.parquimetro.model.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um repositório Spring
public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

    // Verifica se um veículo já existe pela placa
    boolean existsByPlaca(String placa);
}
