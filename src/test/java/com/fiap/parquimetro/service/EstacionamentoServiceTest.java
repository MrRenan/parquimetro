package com.fiap.parquimetro.service;

import com.fiap.parquimetro.dto.AtualizarSaidaEstacionamentoDTO;
import com.fiap.parquimetro.dto.IniciarEstacionamentoDTO;
import com.fiap.parquimetro.enums.TipoPeriodoEstacionamento;
import com.fiap.parquimetro.mapper.EstacionamentoMapper;
import com.fiap.parquimetro.model.Estacionamento;
import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.repository.EstacionamentoRepository;
import com.fiap.parquimetro.repository.MotoristaRepository;
import com.fiap.parquimetro.repository.PagamentoRepository;
import com.fiap.parquimetro.repository.VeiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
/**
 * Resumo:
 * A classe EstacionamentoServiceTest testa as operações principais de EstacionamentoService,
 * incluindo a criação de estacionamentos e a validação de dados relacionados a motoristas e veículos.
 */
public class EstacionamentoServiceTest {

    @Mock
    private MotoristaRepository motoristaRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private EstacionamentoRepository estacionamentoRepository;

    @Mock
    private EstacionamentoMapper estacionamentoMapper;

    @Mock
    private SQSService sqsService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private PagamentoRepository formaPagamentoRepository;

    @InjectMocks
    private EstacionamentoService estacionamentoService;

    private IniciarEstacionamentoDTO iniciarEstacionamentoDTO;
    private Motorista motorista;
    private Veiculo veiculo;

    /**
     * Configura os dados necessários para os testes antes de cada execução.
     */
    @BeforeEach
    public void setUp() {
        iniciarEstacionamentoDTO = new IniciarEstacionamentoDTO();
        iniciarEstacionamentoDTO.setMotoristaId("motoristaId");
        iniciarEstacionamentoDTO.setVeiculoId("veiculoId");
        iniciarEstacionamentoDTO.setTipo(TipoPeriodoEstacionamento.PERIODO_FIXO);
        iniciarEstacionamentoDTO.setSaida(LocalDateTime.now().plusHours(1));

        motorista = Motorista.builder()
                .id("motoristaId")
                .nome("Nome")
                .endereco("Endereco")
                .email("email@example.com")
                .build();

        veiculo = Veiculo.builder()
                .id("veiculoId")
                .modelo("Modelo")
                .placa("ABC-1234")
                .build();
    }

    /**
     * Testa o cenário onde o motorista não é encontrado.
     */
    @Test
    public void testCriarEstacionamentoMotoristaNaoEncontrado() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(DataIntegrityViolationException.class, () -> estacionamentoService.criarEstacionamento(iniciarEstacionamentoDTO));
    }

    /**
     * Testa o cenário onde o veículo não é encontrado.
     */
    @Test
    public void testCriarEstacionamentoVeiculoNaoEncontrado() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.of(motorista));
        when(veiculoRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(DataIntegrityViolationException.class, () -> estacionamentoService.criarEstacionamento(iniciarEstacionamentoDTO));
    }

    /**
     * Testa o cenário onde o veículo não está associado ao motorista.
     */
    @Test
    public void testCriarEstacionamentoVeiculoNaoAssociadoAoMotorista() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.of(motorista));
        when(veiculoRepository.findById(anyString())).thenReturn(Optional.of(veiculo));
        assertThrows(DataIntegrityViolationException.class, () -> estacionamentoService.criarEstacionamento(iniciarEstacionamentoDTO));
    }

    /**
     * Testa o cenário onde a saída não é informada para um período fixo.
     */
    @Test
    public void testCriarEstacionamentoPeriodoFixoSemSaida() {
        iniciarEstacionamentoDTO.setSaida(null);
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.of(motorista));
        when(veiculoRepository.findById(anyString())).thenReturn(Optional.of(veiculo));
        motorista.setVeiculos(List.of(veiculo));
        assertThrows(DataIntegrityViolationException.class, () -> estacionamentoService.criarEstacionamento(iniciarEstacionamentoDTO));
    }
}
