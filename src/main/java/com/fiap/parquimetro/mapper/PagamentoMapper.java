package com.fiap.parquimetro.mapper;

import com.fiap.parquimetro.dto.PagamentoDTO;
import com.fiap.parquimetro.model.Pagamento;
import org.mapstruct.Mapper;

@Mapper // Indica que esta interface é um mapper do MapStruct
public interface PagamentoMapper {

    // Converte uma entidade Pagamento para um DTO de Pagamento
    PagamentoDTO toDTO(Pagamento formaPagamento);

    // Converte um DTO de Pagamento para uma entidade Pagamento
    Pagamento toEntity(PagamentoDTO dto);
}
