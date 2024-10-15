package com.fiap.parquimetro.model;

import com.fiap.parquimetro.enums.TipoPagamento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "formaPagamento") // Indica que essa classe representa um documento MongoDB
@Setter // Lombok para gerar os métodos setters
@Getter // Lombok para gerar os métodos getters
@Builder // Lombok para gerar um construtor Builder
public class Pagamento {

    @Id // Indica que este campo é o identificador único do documento
    private String id;

    private String descricao; // Descrição do pagamento
    private TipoPagamento tipo; // Tipo do pagamento
    private String motoristaId; // Identificador do motorista associado ao pagamento
}
