package br.com.rafaellima.demo.controller;

import br.com.rafaellima.demo.dto.DespesaRequestDTO;
import br.com.rafaellima.demo.dto.DespesaResponseDTO;
import br.com.rafaellima.demo.dto.ListarDespesasDTO;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.service.DespesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/despesas")
@RequiredArgsConstructor
public class DespesaController {
   private final DespesaService despesaService;

   @GetMapping
   public ResponseEntity<Page<ListarDespesasDTO>> listarDespesas(@PageableDefault(page = 0, size = 10) Pageable paginacao, @AuthenticationPrincipal Usuario usuarioLogado) {
      return ResponseEntity.ok(despesaService.listar(paginacao, usuarioLogado));
   }

   @GetMapping("/{id}")
   public ResponseEntity<ListarDespesasDTO> detalharDespesa(@PathVariable Long id,
                                            @AuthenticationPrincipal Usuario usuarioLogado) {
      var despesaDetalhada = despesaService.detalhar(id, usuarioLogado);
      return ResponseEntity.ok(despesaDetalhada);
   }


   @PostMapping
   public ResponseEntity<Void> criarDespesa(@RequestBody @Valid DespesaRequestDTO requestDTO,
                                         @AuthenticationPrincipal Usuario usuarioLogado) {
      var despesaCriada = despesaService.criar(requestDTO, usuarioLogado);

      URI location =
            ServletUriComponentsBuilder
                  .fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(despesaCriada.getId())
                  .toUri();
      return ResponseEntity.created(location).build();
   }
}
