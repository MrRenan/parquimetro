package com.fiap.parquimetro.enums;

// Enumeração dos tipos de período de estacionamento disponíveis
public enum TipoPeriodoEstacionamento {
    PERIODO_FIXO("Período Fixo"), // Estacionamento por um período fixo
    POR_HORA("Por Hora"); // Estacionamento por hora

    private final String descricao; // Descrição do período de estacionamento

    TipoPeriodoEstacionamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
