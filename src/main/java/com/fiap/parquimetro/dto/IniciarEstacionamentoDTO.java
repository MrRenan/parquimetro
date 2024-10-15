package com.fiap.parquimetro.dto;

import com.fiap.parquimetro.enums.TipoPeriodoEstacionamento;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
public class IniciarEstacionamentoDTO {

    private LocalDateTime entrada; // Data e hora de entrada do veículo no estacionamento
    private LocalDateTime saida; // Data e hora de saída do veículo do estacionamento
    private TipoPeriodoEstacionamento tipo; // Tipo de período de estacionamento
    private String motoristaId; // Identificador do motorista
    private String veiculoId; // Identificador do veículo
}
