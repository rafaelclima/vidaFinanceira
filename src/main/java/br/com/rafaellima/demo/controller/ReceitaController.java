package br.com.rafaellima.demo.controller;

import br.com.rafaellima.demo.model.Receita;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rafaellima.demo.dto.ListarReceitasDTO;
import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.dto.ReceitaResponseDTO;
import br.com.rafaellima.demo.dto.ReceitaUpdateRequestDTO;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.service.ReceitaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/receitas")
@RequiredArgsConstructor
public class ReceitaController {

  private final ReceitaService receitaService;

  @GetMapping
  public ResponseEntity<Page<ListarReceitasDTO>> listarReceitas(
      @PageableDefault(page = 0, size = 10) Pageable paginacao, @AuthenticationPrincipal Usuario usuarioLogado) {
    return ResponseEntity.ok(receitaService.listar(paginacao, usuarioLogado));
  }

  @PostMapping
  @Transactional
  public ResponseEntity<Void> criar(@RequestBody @Valid ReceitaRequestDTO receitaRequest,
      @AuthenticationPrincipal Usuario usuarioLogado) {

    var receitaCriada = receitaService.cadastrarReceita(receitaRequest, usuarioLogado);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(receitaCriada.getId())
        .toUri();

    return ResponseEntity.created(location).build();

  }

  @GetMapping("/{id}")
  public ResponseEntity<ReceitaResponseDTO> detalheReceita(@PathVariable Long id,
      @AuthenticationPrincipal Usuario usuarioLogado) {
    var receita = receitaService.detalhar(id, usuarioLogado);
    return ResponseEntity.ok(new ReceitaResponseDTO(
         receita.getId(),
         receita.getDescricao(),
         receita.getValor(),
         receita.getDataRecebimento(),
         receita.getStatus(),
         receita.getFonte()
    ));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReceitaResponseDTO> atualizarReceita(@PathVariable Long id,
      @RequestBody ReceitaUpdateRequestDTO requestDTO, @AuthenticationPrincipal Usuario usuario) {

    Receita receitaAtualizada = receitaService.atualizarReceita(id, requestDTO, usuario);
    return ResponseEntity.ok(new ReceitaResponseDTO(
         receitaAtualizada.getId(),
         receitaAtualizada.getDescricao(),
         receitaAtualizada.getValor(),
         receitaAtualizada.getDataRecebimento(),
         receitaAtualizada.getStatus(),
         receitaAtualizada.getFonte()
    ));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarReceita(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
    receitaService.deletarReceita(id, usuario);
    return ResponseEntity.noContent().build();
  }

}
