package com.fiap.parquimetro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "motoristas") // Indica que essa classe representa um documento MongoDB
@Setter // Lombok para gerar os métodos setters
@Getter // Lombok para gerar os métodos getters
@Builder // Lombok para gerar um construtor Builder
@AllArgsConstructor // Lombok para gerar um construtor com todos os campos
public class Motorista {

    @Id // Indica que este campo é o identificador único do documento
    private String id;

    private String nome; // Nome do motorista
    private String endereco; // Endereço do motorista
    private String email; // Email do motorista

    @DBRef // Indica uma referência a outro documento no MongoDB
    private List<Veiculo> veiculos; // Lista de veículos associados ao motorista

    @DBRef // Indica uma referência a outro documento no MongoDB
    private List<Pagamento> pagamentos; // Lista de pagamentos associados ao motorista
}
