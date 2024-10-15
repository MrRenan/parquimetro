package com.fiap.parquimetro.service;

import com.fiap.parquimetro.dto.VeiculoDTO;
import com.fiap.parquimetro.mapper.VeiculoMapper;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private VeiculoMapper veiculoMapper;

    @InjectMocks
    private VeiculoService veiculoService;

    private VeiculoDTO veiculoDTO;
    private Veiculo veiculo;

    @BeforeEach
    public void setUp() {
        veiculoDTO = new VeiculoDTO();
        veiculoDTO.setId("veiculoId");
        veiculoDTO.setModelo("Modelo Exemplo");
        veiculoDTO.setPlaca("ABC-1234");

        veiculo = Veiculo.builder()
                .id("veiculoId")
                .modelo("Modelo Exemplo")
                .placa("ABC-1234")
                .build();
    }

    @Test
    public void testObterVeiculoPorId() {
        when(veiculoRepository.findById(anyString())).thenReturn(Optional.of(veiculo));
        when(veiculoMapper.toDTO(veiculo)).thenReturn(veiculoDTO);

        Optional<VeiculoDTO> resultado = veiculoService.obterVeiculoPorId("veiculoId");
        assertTrue(resultado.isPresent());
        assertEquals(veiculoDTO, resultado.get());
    }

    // Outros testes conforme necessário
}
