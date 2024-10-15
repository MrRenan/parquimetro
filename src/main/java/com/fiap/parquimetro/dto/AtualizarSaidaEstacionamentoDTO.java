package com.fiap.parquimetro.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
public class AtualizarSaidaEstacionamentoDTO {

    private LocalDateTime saida; // Data e hora da saída do estacionamento
    private String pagamentoId; // Identificador do pagamento
}
