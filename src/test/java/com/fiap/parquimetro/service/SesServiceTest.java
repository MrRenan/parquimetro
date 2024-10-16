package com.fiap.parquimetro.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
/**
 * Resumo:
 * A classe SesServiceTest testa as operações principais de SesService,
 * incluindo a configuração do cliente SES e o envio de emails utilizando
 * o serviço AWS SES (Simple Email Service).
 */
public class SesServiceTest {

    @Mock
    private SesClient sesClient;

    @InjectMocks
    private SesService sesService;

    private SendEmailRequest sendEmailRequest;

    /**
     * Configura os dados necessários para os testes antes de cada execução.
     */
    @BeforeEach
    public void setUp() {
        sendEmailRequest = SendEmailRequest.builder()
                .source("noreply@example.com")
                .destination(d -> d.toAddresses("to@example.com"))
                .message(m -> m
                        .subject(s -> s.data("Assunto do Email"))
                        .body(b -> b.text(t -> t.data("Conteúdo do email"))))
                .build();
    }

    /**
     * Testa o envio de email utilizando o serviço SES.
     */
     @Disabled
     @Test
    public void testSendMessage() {
        SendEmailResponse sendEmailResponse = SendEmailResponse.builder().messageId("1234567890").build();
        when(sesClient.sendEmail(any(SendEmailRequest.class))).thenReturn(sendEmailResponse);

        sesService.sendMessage(sendEmailRequest);

        verify(sesClient, times(1)).sendEmail(sendEmailRequest);
    }

    // Outros testes conforme necessário
}