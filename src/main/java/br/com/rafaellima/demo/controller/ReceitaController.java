package br.com.rafaellima.demo.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rafaellima.demo.dto.ListarReceitasDTO;
import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.dto.ReceitaResponseDTO;
import br.com.rafaellima.demo.dto.ReceitaUpdateRequestDTO;
import br.com.rafaellima.demo.model.Receita;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/receitas")
@RequiredArgsConstructor
@Tag(name = "Receitas", description = "Endpoints para gerenciamento de receitas")
public class ReceitaController {

  private final ReceitaService receitaService;

  @GetMapping
  @Operation(summary = "Lista todas as receitas do usuário", description = "Retorna uma lista paginada de todas as receitas associadas ao usuário autenticado.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso")
  })
  public ResponseEntity<Page<ListarReceitasDTO>> listarReceitas(
      @PageableDefault(page = 0, size = 10) Pageable paginacao, 
      @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
    return ResponseEntity.ok(receitaService.listar(paginacao, usuarioLogado));
  }

  @PostMapping
  @Operation(summary = "Cria uma nova receita", description = "Registra uma nova receita para o usuário autenticado.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Receita criada com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
  })
  public ResponseEntity<Void> criar(@RequestBody @Valid ReceitaRequestDTO receitaRequest,
      @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {

    var receitaCriada = receitaService.cadastrarReceita(receitaRequest, usuarioLogado);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(receitaCriada.getId())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Detalha uma receita", description = "Retorna os detalhes de uma receita específica, se pertencer ao usuário autenticado.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Detalhes da receita retornados com sucesso"),
      @ApiResponse(responseCode = "404", description = "Receita não encontrada")
  })
  public ResponseEntity<ReceitaResponseDTO> detalheReceita(@PathVariable Long id,
      @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
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
  @Operation(summary = "Atualiza uma receita", description = "Atualiza os dados de uma receita existente, se pertencer ao usuário autenticado.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
      @ApiResponse(responseCode = "404", description = "Receita não encontrada")
  })
  public ResponseEntity<ReceitaResponseDTO> atualizarReceita(@PathVariable Long id,
      @RequestBody ReceitaUpdateRequestDTO requestDTO, 
      @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario) {

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
  @Operation(summary = "Deleta uma receita", description = "Exclui uma receita existente, se pertencer ao usuário autenticado.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Receita deletada com sucesso"),
      @ApiResponse(responseCode = "404", description = "Receita não encontrada")
  })
  public ResponseEntity<Void> deletarReceita(@PathVariable Long id, 
      @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario) {
    receitaService.deletarReceita(id, usuario);
    return ResponseEntity.noContent().build();
  }
}
