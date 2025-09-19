package br.com.rafaellima.demo.dto;

import br.com.rafaellima.demo.model.StatusDespesa;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListarDespesasDTO(
      Long id,
      String descricao,
      BigDecimal valor,
      LocalDate dataVencimento,
      StatusDespesa status
) {
}
