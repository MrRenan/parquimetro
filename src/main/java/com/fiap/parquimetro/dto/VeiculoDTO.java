package com.fiap.parquimetro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
public class VeiculoDTO {

    private String id; // Identificador do veículo
    private String modelo; // Modelo do veículo
    private String placa; // Placa do veículo
}
