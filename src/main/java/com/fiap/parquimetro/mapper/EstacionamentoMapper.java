package com.fiap.parquimetro.mapper;

import com.fiap.parquimetro.dto.IniciarEstacionamentoDTO;
import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Estacionamento;
import org.mapstruct.Mapper;

@Mapper // Indica que esta interface é um mapper do MapStruct
public interface EstacionamentoMapper {

    // Converte um DTO de IniciarEstacionamento para uma entidade Estacionamento
    Estacionamento toEntity(IniciarEstacionamentoDTO dto);

    // Converte uma entidade Motorista para um DTO de IniciarEstacionamento
    IniciarEstacionamentoDTO toDTO(Motorista entity);
}
