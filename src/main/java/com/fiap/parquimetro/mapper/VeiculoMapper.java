package com.fiap.parquimetro.mapper;

import com.fiap.parquimetro.dto.VeiculoDTO;
import com.fiap.parquimetro.model.Veiculo;
import org.mapstruct.Mapper;

@Mapper // Indica que esta interface é um mapper do MapStruct
public interface VeiculoMapper {

    // Converte um DTO de Veiculo para uma entidade Veiculo
    Veiculo toEntity(VeiculoDTO dto);

    // Converte uma entidade Veiculo para um DTO de Veiculo
    VeiculoDTO toDTO(Veiculo entity);
}
