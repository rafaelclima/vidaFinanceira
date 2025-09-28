package br.com.rafaellima.demo.controller;

import br.com.rafaellima.demo.dto.DespesaPorCategoriaDTO;
import br.com.rafaellima.demo.dto.ResumoMensalDTO;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.service.RelatoriosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class RelatoriosController {

   private final RelatoriosService relatoriosService;

   @GetMapping("/{ano}/{mes}")
   @Operation(summary = "Gera um resumo mensal", description = "Retorna um resumo das receitas e despesas para um determinado mês e ano.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
   })
   public ResponseEntity<ResumoMensalDTO> gerarRelatorioMensal(@PathVariable int ano, @PathVariable int mes,
                                                               @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
       var relatorio = relatoriosService.gerarRelatorioMensal(ano, mes, usuarioLogado);
       return ResponseEntity.ok(relatorio);
   }

   @GetMapping("/despesas-por-categoria/{ano}/{mes}")
   @Operation(summary = "Gera relatório de despesas por categoria", description = "Retorna uma lista de despesas agrupadas por categoria para um determinado mês e ano.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
   })
   public ResponseEntity<List<DespesaPorCategoriaDTO>> gerarDespesaPorCategoria(@PathVariable int ano,
                                                         @PathVariable int mes,
                                                        @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
       var relatorio = relatoriosService.gerarDespesaPorCategoria(ano, mes, usuarioLogado);
       return ResponseEntity.ok(relatorio);
   }

}
