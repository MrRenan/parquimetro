package com.fiap.parquimetro.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

/**
 * Resumo:
 * A classe EmailService é responsável por enviar emails utilizando o serviço AWS SES. Ela contém
 * um método para enviar mensagens de email com base nas solicitações recebidas.
 */
@Service // Indica que esta classe é um serviço do Spring
@AllArgsConstructor(onConstructor = @__(@Autowired)) // Lombok para gerar um construtor com todos os campos finais e anotado com @Autowired
public class EmailService {

    private final SesService sesService; // Serviço para envio de email via AWS SES

    public void sendMessage(SendEmailRequest sendEmailRequest) {
        // Envia uma mensagem de email usando o serviço SES
        this.sesService.sendMessage(sendEmailRequest);
    }
}
