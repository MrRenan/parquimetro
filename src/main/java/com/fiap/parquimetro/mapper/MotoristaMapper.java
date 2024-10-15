package com.fiap.parquimetro.mapper;

import com.fiap.parquimetro.dto.MotoristaDTO;
import com.fiap.parquimetro.model.Motorista;
import org.mapstruct.Mapper;

@Mapper // Indica que esta interface é um mapper do MapStruct
public interface MotoristaMapper {

    // Converte um DTO de Motorista para uma entidade Motorista
    Motorista toEntity(MotoristaDTO dto);

    // Converte uma entidade Motorista para um DTO de Motorista
    MotoristaDTO toDTO(Motorista entity);
}
