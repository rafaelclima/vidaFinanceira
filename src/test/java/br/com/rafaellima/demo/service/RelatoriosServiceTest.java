package br.com.rafaellima.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.exception.DataFuturaException;
import br.com.rafaellima.demo.repository.DespesasRepository;
import br.com.rafaellima.demo.repository.ReceitasRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

    @Test
    void gerarRelatorioMensal_ParaDataFutura_DeveLancarIllegalArgumentException() {
        // 1. Arrange
        LocalDate hoje = LocalDate.now();
        int anoFuturo = hoje.plusMonths(1).getYear();
        int mesFuturo = hoje.plusMonths(1).getMonthValue();
        var usuario = new br.com.rafaellima.demo.model.Usuario();
        usuario.setId(1L);

        String mensagemEsperada = "Não é possível gerar relatório para datas futuras.";

        // 2. Act & 3. Assert
        var exception = assertThrows(DataFuturaException.class, () -> {
            // O código que deve lançar a exceção é executado aqui dentro
            relatoriosService.gerarRelatorioMensal(anoFuturo, mesFuturo, usuario);
        });

        // Opcional, mas uma boa prática: verificar se a mensagem da exceção está correta
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void gerarDespesaPorCategoria_ComDadosValidos_DeveRetornarListaDeDTOs() {
        // 1. Arrange
        int ano = 2025;
        int mes = 9;
        var usuario = new br.com.rafaellima.demo.model.Usuario();
        usuario.setId(1L);

        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        // Preparamos a lista que o nosso mock deve retornar
        var listaMock = java.util.List.of(
                new br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO(br.com.rafaellima.demo.model.CategoriaDespesa.MORADIA, new BigDecimal("1500.00")),
                new br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO(br.com.rafaellima.demo.model.CategoriaDespesa.ALIMENTACAO, new BigDecimal("750.50"))
        );

        when(despesasRepository.calcularDespesasPorCategoria(usuario.getId(), dataInicio, dataFim))
                .thenReturn(listaMock);

        // 2. Act
        var resultadoDTO = relatoriosService.gerarDespesaPorCategoria(ano, mes, usuario);

        // 3. Assert
        assertNotNull(resultadoDTO);
        assertEquals(2, resultadoDTO.size());
        // Verifica o primeiro item da lista (o de maior valor, conforme nossa query)
        assertEquals(br.com.rafaellima.demo.model.CategoriaDespesa.MORADIA, resultadoDTO.getFirst().categoria());
        assertEquals(new BigDecimal("1500.00"), resultadoDTO.getFirst().totalGasto());
    }
}