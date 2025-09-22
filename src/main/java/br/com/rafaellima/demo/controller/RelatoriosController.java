package br.com.rafaellima.demo.controller;

import br.com.rafaellima.demo.dto.ResumoMensalDTO;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.service.RelatoriosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatoriosController {

   private final RelatoriosService relatoriosService;

   @GetMapping("/{ano}/{mes}")
   public ResponseEntity<ResumoMensalDTO> gerarRelatorioMensal(@PathVariable int ano, @PathVariable int mes,
                                                               @AuthenticationPrincipal Usuario usuarioLogado) {
       var relatorio = relatoriosService.gerarRelatorioMensal(ano, mes, usuarioLogado);
       return ResponseEntity.ok(relatorio);
   }

}
