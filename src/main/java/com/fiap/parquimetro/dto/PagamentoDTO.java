package com.fiap.parquimetro.dto;

import com.fiap.parquimetro.enums.TipoPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter // Lombok para gerar os métodos getters
@Setter // Lombok para gerar os métodos setters
public class PagamentoDTO {

    private String id; // Identificador do pagamento
    private String descricao; // Descrição do pagamento
    private TipoPagamento tipo; // Tipo do pagamento
    private String motoristaId; // Identificador do motorista
}
