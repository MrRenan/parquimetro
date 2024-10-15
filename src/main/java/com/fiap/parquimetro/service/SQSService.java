package com.fiap.parquimetro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Resumo:
 * A classe SQSService configura o cliente AWS SQS (Simple Queue Service) para envio de mensagens.
 * Ela permite substituir o endpoint padrão para desenvolvimento local e envia mensagens para a
 * fila especificada utilizando as credenciais e região especificadas.
 */
@Service
@Slf4j
public class SQSService {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public SQSService(@Value("${aws.sqs.queueUrl}") String queueUrl,
                      @Value("${aws.region}") String region,
                      @Value("${aws.local.endpoint:#{null}}") String awsEndpoint) {
        this.queueUrl = queueUrl;
        SqsClientBuilder builder = SqsClient.builder();
        if (awsEndpoint != null) {
            // Substitui o endpoint AWS com a URL do localstack no ambiente de desenvolvimento
            try {
                builder.endpointOverride(new URI(awsEndpoint));
            } catch (URISyntaxException ex) {
                log.error("Invalid url {}", awsEndpoint);
                throw new IllegalStateException("Invalid url " + awsEndpoint, ex);
            }
        }
        sqsClient = builder.credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.of(region))
                .build();
    }

    public void sendMessage(String message) {
        log.debug("Sending message: {}", message);
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();
        sqsClient.sendMessage(request);
    }
}
