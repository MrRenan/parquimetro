package com.fiap.parquimetro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private SesService sesService;

    @InjectMocks
    private EmailService emailService;

    private SendEmailRequest sendEmailRequest;

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

    @Test
    public void testSendMessage() {
        doNothing().when(sesService).sendMessage(any(SendEmailRequest.class));

        emailService.sendMessage(sendEmailRequest);

        verify(sesService, times(1)).sendMessage(sendEmailRequest);
    }
}
