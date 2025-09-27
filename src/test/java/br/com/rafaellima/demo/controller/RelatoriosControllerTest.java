package br.com.rafaellima.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.repository.DespesasRepository;
import br.com.rafaellima.demo.repository.ReceitasRepository;
import br.com.rafaellima.demo.service.AuthService;

@SpringBootTest
@AutoConfigureMockMvc
class RelatoriosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceitasRepository receitasRepository;

    @MockBean
    private DespesasRepository despesasRepository;

    @MockBean
    private AuthService authService; // Necessário para o contexto de segurança

    @Test
    void gerarRelatorioMensal_ComDadosValidos_DeveRetornarStatus200EResumo() throws Exception {
        // Arrange
        int ano = 2025;
        int mes = 9;
        long usuarioId = 1L;
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        BigDecimal totalReceitas = new BigDecimal("10000.00");
        BigDecimal totalDespesas = new BigDecimal("7500.00");

        when(receitasRepository.calcularTotalReceitasNoPeriodo(usuarioId, dataInicio, dataFim))
                .thenReturn(totalReceitas);
        when(despesasRepository.calcularTotalDespesasNoPeriodo(usuarioId, dataInicio, dataFim))
                .thenReturn(totalDespesas);

        // Act & Assert
        mockMvc.perform(
                get("/api/relatorios/{ano}/{mes}", ano, mes)
                        .with(user(new Usuario(usuarioId, "user", "user@test.com", "password", null, null)))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalReceitas").value(10000.00))
                .andExpect(jsonPath("$.totalDespesas").value(7500.00))
                .andExpect(jsonPath("$.saldoFinal").value(2500.00));
    }

    @Test
    void gerarDespesaPorCategoria_ComDadosValidos_DeveRetornarStatus200EListaDeDTOs() throws Exception {
        // Arrange
        int ano = 2025;
        int mes = 9;
        long usuarioId = 1L;
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        var listaMock = java.util.List.of(
                new br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO(br.com.rafaellima.demo.model.CategoriaDespesa.MORADIA, new BigDecimal("1500.00")),
                new br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO(br.com.rafaellima.demo.model.CategoriaDespesa.ALIMENTACAO, new BigDecimal("750.50"))
        );

        when(despesasRepository.calcularDespesasPorCategoria(usuarioId, dataInicio, dataFim))
                .thenReturn(listaMock);

        // Act & Assert
        mockMvc.perform(
                get("/api/relatorios/despesas-por-categoria/{ano}/{mes}", ano, mes)
                        .with(user(new Usuario(usuarioId, "user", "user@test.com", "password", null, null)))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoria").value("MORADIA"))
                .andExpect(jsonPath("$[0].totalGasto").value(1500.00))
                .andExpect(jsonPath("$[1].categoria").value("ALIMENTACAO"))
                .andExpect(jsonPath("$[1].totalGasto").value(750.50));
    }
}