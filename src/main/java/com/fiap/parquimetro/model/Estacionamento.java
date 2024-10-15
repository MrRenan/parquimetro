package com.fiap.parquimetro.model;

import com.fiap.parquimetro.enums.TipoPeriodoEstacionamento;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "estacionamentos") // Indica que essa classe representa um documento MongoDB
@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
public class Estacionamento {

    @Id // Indica que este campo é o identificador único do documento
    private String id;

    private LocalDateTime entrada; // Data e hora de entrada do veículo no estacionamento
    private LocalDateTime saida; // Data e hora de saída do veículo do estacionamento
    private TipoPeriodoEstacionamento tipo; // Tipo de período de estacionamento

    @DBRef // Indica uma referência a outro documento no MongoDB
    private Motorista motorista;

    @DBRef // Indica uma referência a outro documento no MongoDB
    private Veiculo veiculo;

    @Null // Indica que o campo pode ser nulo
    @DBRef // Indica uma referência a outro documento no MongoDB
    private Pagamento pagamento;

    @Null // Indica que o campo pode ser nulo
    private double valor; // Valor a ser pago pelo estacionamento
}
