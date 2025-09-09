package br.com.rafaellima.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaResponseDTO(
      Long id,
      String descricao,
      BigDecimal valor,
      LocalDate dataVencimento,
      String categoria,
      String metodoPagamento,
      String status
) {
}
