package com.fiap.parquimetro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SQSServiceTest {

    @Mock
    private SqsClient sqsClient;

    @InjectMocks
    private SQSService sqsService = new SQSService("http://localhost:4566/000000000000/queue-name", "us-east-1", "http://localhost:4566");

    private SendMessageRequest sendMessageRequest;

    @BeforeEach
    public void setUp() {
        sendMessageRequest = SendMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/queue-name")
                .messageBody("Test Message")
                .build();
    }

    @Disabled
    @Test
    public void testSendMessage() {
        SendMessageResponse sendMessageResponse = SendMessageResponse.builder().messageId("1234567890").build();
        when(sqsClient.sendMessage(any(SendMessageRequest.class))).thenReturn(sendMessageResponse);

        sqsService.sendMessage("Test Message");

        verify(sqsClient, times(1)).sendMessage(sendMessageRequest);
    }
}
