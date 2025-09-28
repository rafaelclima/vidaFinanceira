package br.com.rafaellima.demo.controller;

import br.com.rafaellima.demo.dto.DespesaRequestDTO;
import br.com.rafaellima.demo.dto.DespesaResponseDTO;
import br.com.rafaellima.demo.dto.DespesaUpdateRequestDTO;
import br.com.rafaellima.demo.dto.ListarDespesasDTO;
import br.com.rafaellima.demo.model.Usuario;
import br.com.rafaellima.demo.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Despesas", description = "Endpoints para gerenciamento de despesas")
public class DespesaController {
   private final DespesaService despesaService;

   @GetMapping
   @Operation(summary = "Lista todas as despesas do usuário", description = "Retorna uma lista paginada de todas as despesas associadas ao usuário autenticado.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso")
   })
   public ResponseEntity<Page<ListarDespesasDTO>> listarDespesas(@PageableDefault(page = 0, size = 10) Pageable paginacao, @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
      return ResponseEntity.ok(despesaService.listar(paginacao, usuarioLogado));
   }

   @GetMapping("/{id}")
   @Operation(summary = "Detalha uma despesa", description = "Retorna os detalhes de uma despesa específica, se pertencer ao usuário autenticado.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Detalhes da despesa retornados com sucesso"),
         @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
   })
   public ResponseEntity<ListarDespesasDTO> detalharDespesa(@PathVariable Long id,
                                            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
      var despesaDetalhada = despesaService.detalhar(id, usuarioLogado);
      return ResponseEntity.ok(despesaDetalhada);
   }


   @PostMapping
   @Operation(summary = "Cria uma nova despesa", description = "Registra uma nova despesa para o usuário autenticado.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso"),
         @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
   })
   public ResponseEntity<Void> criarDespesa(@RequestBody @Valid DespesaRequestDTO requestDTO,
                                         @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
      var despesaCriada = despesaService.criar(requestDTO, usuarioLogado);

      URI location =
            ServletUriComponentsBuilder
                  .fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(despesaCriada.getId())
                  .toUri();
      return ResponseEntity.created(location).build();
   }

   @PutMapping("/{id}")
   @Operation(summary = "Atualiza uma despesa", description = "Atualiza os dados de uma despesa existente, se pertencer ao usuário autenticado.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso"),
         @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
         @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
   })
   public ResponseEntity<DespesaResponseDTO> atualizarDespesa(@PathVariable Long id,
                                                @RequestBody @Valid DespesaUpdateRequestDTO requestDTO,
                                                @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
      var despesaAtualizada = despesaService.atualizar(id, requestDTO, usuarioLogado);

      return ResponseEntity.ok(new DespesaResponseDTO(
            despesaAtualizada.getId(),
            despesaAtualizada.getDescricao(),
            despesaAtualizada.getValor(),
            despesaAtualizada.getDataVencimento(),
            despesaAtualizada.getCategoria().name(),
            despesaAtualizada.getMetodoPagamento().name(),
            despesaAtualizada.getStatus().name()
      ));
   }

   @DeleteMapping("/{id}")
   @Operation(summary = "Deleta uma despesa", description = "Exclui uma despesa existente, se pertencer ao usuário autenticado.")
   @ApiResponses(value = {
         @ApiResponse(responseCode = "204", description = "Despesa deletada com sucesso"),
         @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
   })
   public ResponseEntity<Void> deletarDespesa(@PathVariable Long id,
                                              @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuarioLogado) {
      despesaService.deletar(id, usuarioLogado);
      return ResponseEntity.noContent().build();
   }

}
