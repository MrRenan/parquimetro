package com.fiap.parquimetro.service;

import com.fiap.parquimetro.dto.PagamentoDTO;
import com.fiap.parquimetro.enums.TipoPagamento;
import com.fiap.parquimetro.mapper.PagamentoMapper;
import com.fiap.parquimetro.model.Motorista;
import com.fiap.parquimetro.model.Pagamento;
import com.fiap.parquimetro.repository.MotoristaRepository;
import com.fiap.parquimetro.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
/**
 * Resumo:
 * A classe PagamentoServiceTest testa as operações principais de PagamentoService,
 * incluindo criação, busca, e listagem de pagamentos, além de verificar se os pagamentos
 * estão corretamente associados aos motoristas.
 */
public class PagamentoServiceTest {

    @Mock
    private MotoristaRepository motoristaRepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoMapper pagamentoMapper;

    @InjectMocks
    private PagamentoService pagamentoService;

    private PagamentoDTO pagamentoDTO;
    private Motorista motorista;
    private Pagamento pagamento;

    /**
     * Configura os dados necessários para os testes antes de cada execução.
     */
    @BeforeEach
    public void setUp() {
        pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setMotoristaId("motoristaId");

        motorista = Motorista.builder()
                .id("motoristaId")
                .nome("Nome")
                .endereco("Endereco")
                .email("email@example.com")
                .build();

        pagamento = Pagamento.builder()
                .id("pagamentoId")
                .descricao("Descricao")
                .tipo(TipoPagamento.CARTAO_CREDITO)
                .motoristaId("motoristaId")
                .build();
    }

    /**
     * Testa o cenário onde o motorista não é encontrado.
     */
    @Test
    public void testCriarPagamentoMotoristaNaoEncontrado() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(DataIntegrityViolationException.class, () -> pagamentoService.criarPagamento(pagamentoDTO));
    }

    /**
     * Testa o cenário de criação bem-sucedida de pagamento.
     */
    @Test
    public void testCriarPagamentoSucesso() {
        when(motoristaRepository.findById(anyString())).thenReturn(Optional.of(motorista));
        when(pagamentoMapper.toEntity(any(PagamentoDTO.class))).thenReturn(pagamento);
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);
        when(pagamentoMapper.toDTO(any(Pagamento.class))).thenReturn(pagamentoDTO);

        PagamentoDTO resultado = pagamentoService.criarPagamento(pagamentoDTO);
        verify(pagamentoRepository).save(pagamento);
    }

    /**
     * Testa a busca de pagamento por ID.
     */
    @Test
    public void testObterPagamento() {
        when(pagamentoRepository.findById(anyString())).thenReturn(Optional.of(pagamento));
        Optional<Pagamento> resultado = pagamentoService.obterPagamento("pagamentoId");
        assert(resultado.isPresent());
    }

    /**
     * Testa a listagem de todos os pagamentos.
     */
    @Test
    public void testFindAll() {
        pagamentoService.findAll();
        verify(pagamentoRepository).findAll();
    }

    /**
     * Testa a exclusão de todos os pagamentos.
     */
    @Test
    public void testDeleteAll() {
        pagamentoService.deleteAll();
        verify(pagamentoRepository).deleteAll();
    }

    // Outros testes conforme necessário
}
