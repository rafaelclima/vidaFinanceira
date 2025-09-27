package br.com.rafaellima.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import br.com.rafaellima.demo.repository.DespesasRepository;
import br.com.rafaellima.demo.repository.ReceitasRepository;

@ExtendWith(MockitoExtension.class)
class RelatoriosServiceTest {

    @Mock
    private DespesasRepository despesasRepository;

    @Mock
    private ReceitasRepository receitasRepository;

    @InjectMocks
    private RelatoriosService relatoriosService;

    @Test
    void gerarRelatorioMensal_ComDadosValidos_DeveRetornarResumoCorreto() {
        // 1. Arrange (Arrumar/Preparar)
        int ano = 2025;
        int mes = 9;
        var usuario = new br.com.rafaellima.demo.model.Usuario();
        usuario.setId(1L);

        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        BigDecimal totalReceitasMock = new BigDecimal("5000.00");
        BigDecimal totalDespesasMock = new BigDecimal("3500.00");

        // "Quando o método X do meu dublê for chamado com ESTES parâmetros, então retorne ESTE valor"
        when(receitasRepository.calcularTotalReceitasNoPeriodo(usuario.getId(), dataInicio, dataFim))
                .thenReturn(totalReceitasMock);
        when(despesasRepository.calcularTotalDespesasNoPeriodo(usuario.getId(), dataInicio, dataFim))
                .thenReturn(totalDespesasMock);

        // 2. Act (Agir/Executar)
        var resumoDTO = relatoriosService.gerarRelatorioMensal(ano, mes, usuario);

        // 3. Assert (Afirmar/Verificar)
        assertNotNull(resumoDTO);
        assertEquals(totalReceitasMock, resumoDTO.totalReceitas());
        assertEquals(totalDespesasMock, resumoDTO.totalDespesas());
        assertEquals(new BigDecimal("1500.00"), resumoDTO.saldoFinal());
    }
}