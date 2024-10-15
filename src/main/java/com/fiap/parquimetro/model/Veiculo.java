package com.fiap.parquimetro.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "veiculos") // Indica que essa classe representa um documento MongoDB
@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
@Builder // Lombok para gerar um construtor Builder
public class Veiculo {

    @Id // Indica que este campo é o identificador único do documento
    private String id;

    private String modelo; // Modelo do veículo
    private String placa; // Placa do veículo
}
