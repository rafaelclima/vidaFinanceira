package br.com.rafaellima.demo.dto;

import br.com.rafaellima.demo.model.CategoriaDespesa;

import java.math.BigDecimal;

public record DespesaPorCategoriaDTO(
      CategoriaDespesa categoria,
      BigDecimal totalGasto
) {
}
