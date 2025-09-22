package br.com.rafaellima.demo.dto;

import java.math.BigDecimal;

public record ResumoMensalDTO(
      BigDecimal totalReceitas,
      BigDecimal totalDespesas,
      BigDecimal saldoFinal
) {
}
