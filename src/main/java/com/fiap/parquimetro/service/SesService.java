package com.fiap.parquimetro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.SesClientBuilder;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Resumo:
 * A classe SesService configura o cliente AWS SES (Simple Email Service) para envio de emails.
 * Ela permite substituir o endpoint padrão para desenvolvimento local e envia emails utilizando
 * as credenciais e região especificadas.
 */
@Service
@Slf4j
public class SesService {

    private final SesClient sesClient;

    public SesService(@Value("${aws.region}") String region,
                      @Value("${aws.local.endpoint:#{null}}") String awsEndpoint) {
        SesClientBuilder builder = SesClient.builder();
        if (awsEndpoint != null) {
            // Substitui o endpoint AWS com a URL do localstack no ambiente de desenvolvimento
            try {
                builder.endpointOverride(new URI(awsEndpoint));
            } catch (URISyntaxException ex) {
                log.error("Invalid url {}", awsEndpoint);
                throw new IllegalStateException("Invalid url " + awsEndpoint, ex);
            }
        }
        sesClient = builder.credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(region))
                .build();
    }

    public void sendMessage(SendEmailRequest sendEmailRequest) {
        // Envia uma mensagem de email usando o cliente SES
        sesClient.sendEmail(sendEmailRequest);
    }
}
