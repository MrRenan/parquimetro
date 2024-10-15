package com.fiap.parquimetro.scheduler;

import com.fiap.parquimetro.enums.TipoPeriodoEstacionamento;
import com.fiap.parquimetro.model.Estacionamento;
import com.fiap.parquimetro.service.EmailService;
import com.fiap.parquimetro.service.EstacionamentoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.model.*;

import java.util.List;

/**
 * Resumo:
 * A classe EstacionamentoScheduler verifica periodicamente se há estacionamentos com o tempo de uso expirando
 * e envia alertas por e-mail aos motoristas. Para estacionamentos de período fixo, ela alerta os motoristas
 * para desligar o registro. Para estacionamentos por hora, ela informa que o sistema estenderá automaticamente
 * o tempo de estacionamento, a menos que o motorista desligue o registro.
 */
@Component // Indica que esta classe é um componente gerenciado pelo Spring
public class EstacionamentoScheduler {

    final EstacionamentoService estacionamentoService;
    final EmailService emailService;

    @Value("${parquimetro.email-noreply}") // Valor injetado da configuração
    String fromEmail;

    private static final String TIME_ZONE = "America/Sao_Paulo"; // Define o fuso horário
    private static final String CADA_5MINUTOS = "0 0/5 * 1/1 * ?"; // Definição de cron para cada 5 minutos

    public EstacionamentoScheduler(EstacionamentoService estacionamentoService, EmailService emailService) {
        this.estacionamentoService = estacionamentoService;
        this.emailService = emailService;
    }

    @Scheduled(cron = CADA_5MINUTOS, zone = TIME_ZONE) // Agendamento da tarefa a cada 5 minutos
    public void verificarExpiracaoTempo() {
        // Busca estacionamentos com tempo expirando
        List<Estacionamento> estacionamentosComTempoExpirando =
                estacionamentoService.encontrarEstacionamentosComTempoExpirando();

        SendEmailRequest.Builder builder = SendEmailRequest.builder();
        builder.source(fromEmail); // Define o remetente do email

        estacionamentosComTempoExpirando.forEach(estacionamento -> {
            System.out.println("Enviando alerta para o condutor " + estacionamento.getMotorista().getNome());
            builder.destination(Destination.builder().toAddresses(estacionamento.getMotorista().getEmail()).build());

            if (TipoPeriodoEstacionamento.PERIODO_FIXO.equals(estacionamento.getTipo())) {
                // Alerta para períodos fixos informando que o tempo de estacionamento está prestes a expirar
                builder.message(
                        Message.builder()
                                .subject(Content.builder().data("Alerta de expiração de tempo de estacionamento").build())
                                .body(Body.builder().text(Content.builder().data("O tempo de estacionamento está prestes a expirar. Desligue o registro.").build()).build())
                                .build());
            }

            if (TipoPeriodoEstacionamento.POR_HORA.equals(estacionamento.getTipo())) {
                // Alerta para períodos variáveis informando que o sistema estenderá automaticamente o estacionamento por mais uma hora
                builder.message(
                        Message.builder()
                                .subject(Content.builder().data("Alerta de expiração de tempo de estacionamento").build())
                                .body(Body.builder().text(Content.builder().data("O tempo de estacionamento está prestes a expirar. O sistema estenderá automaticamente o estacionamento por mais uma hora, a menos que o condutor desligue o registro.").build()).build())
                                .build());
            }

            emailService.sendMessage(builder.build());
        });
    }
}
