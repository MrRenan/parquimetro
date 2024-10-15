package com.fiap.parquimetro.service;

import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Veiculo;
import com.fiap.parquimetro.repository.MotoristaRepository;
import com.fiap.parquimetro.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
/**
 * Resumo:
 * A classe MotoristaServiceTest testa as operações principais de MotoristaService,
 * incluindo registro, atualização, busca e vínculo de veículos aos motoristas.
 */
public class MotoristaServiceTest {

    @Mock
    private MotoristaRepository motoristaRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private MotoristaService motoristaService;

    private Motorista motorista;
    private Veiculo veiculo;

    /**
     * Configura os dados necessários para os testes antes de cada execução.
     */
    @BeforeEach
    public void setUp() {
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
     * Testa o cenário onde o motorista já existe pelo email.
     */
    @Test
    public void testRegistrarMotoristaJaExistente() {
        when(motoristaRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(DataIntegrityViolationException.class, () -> motoristaService.registrarMotorista(motorista));
    }

    /**
     * Testa o cenário de registro bem-sucedido de motorista.
     */
    @Test
    public void testRegistrarMotoristaSucesso() {
        when(motoristaRepository.existsByEmail(anyString())).thenReturn(false);
        when(motoristaRepository.save(any(Motorista.class))).thenReturn(motorista);
        Motorista resultado = motoristaService.registrarMotorista(motorista);
        verify(motoristaRepository).save(motorista);
    }

    /**
     * Testa a busca de motorista por ID.
     */
    @Test
    public void testObterMotoristaPorId() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.of(motorista));
        Optional<Motorista> resultado = motoristaService.obterMotoristaPorId("motoristaId");
        assert(resultado.isPresent());
    }

    /**
     * Testa o cenário onde o motorista não é encontrado.
     */
    @Test
    public void testObterMotoristaPorIdNaoEncontrado() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.empty());
        Optional<Motorista> resultado = motoristaService.obterMotoristaPorId("motoristaId");
        assert(resultado.isEmpty());
    }

    /**
     * Testa a exclusão de todos os motoristas.
     */
    @Test
    public void testDeletarTodosOsMotoristas() {
        motoristaService.deletarTodosOsMotoristas();
        verify(motoristaRepository).deleteAll();
    }

    // Outros testes conforme necessário
}
