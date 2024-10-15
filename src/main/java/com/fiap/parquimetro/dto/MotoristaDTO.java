package com.fiap.parquimetro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
public class MotoristaDTO {

    private String id; // Identificador do motorista
    private String nome; // Nome do motorista
    private String endereco; // Endereço do motorista
    private String email; // Email do motorista
    private String formaPagamentoId; // Identificador da forma de pagamento do motorista
}
