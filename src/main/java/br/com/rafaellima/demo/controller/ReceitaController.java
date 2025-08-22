package br.com.rafaellima.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.rafaellima.demo.dto.ListarReceitasDTO;
import br.com.rafaellima.demo.dto.ReceitaRequestDTO;
import br.com.rafaellima.demo.service.ReceitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/receitas")
@RequiredArgsConstructor
public class ReceitaController {

  private final ReceitaService receitaService;

  @GetMapping
  public ResponseEntity<Page<ListarReceitasDTO>> listarReceitas(
      @PageableDefault(page = 0, size = 10) Pageable paginacao) {
    return ResponseEntity.ok(receitaService.listar(paginacao));
  }

  @PostMapping
  public BodyBuilder criar(@RequestBody @Valid ReceitaRequestDTO receitaRequest) {

    var receitaCriada = receitaService.cadastrarReceita(receitaRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(receitaCriada.getId())
        .toUri();

    return ResponseEntity.created(location);

  }

}
