package br.com.rafaellima.demo.dto;

import br.com.rafaellima.demo.model.CategoriaDespesa;
import br.com.rafaellima.demo.model.MetodoPagamento;
import br.com.rafaellima.demo.model.StatusDespesa;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaUpdateRequestDTO(

      String descricao,

      @Positive(message = "O valor tem que ser maior que 0")
      BigDecimal valor,

      @PastOrPresent(message = "A data de despesa não pode ser futura")
      LocalDate dataDespesa,

      LocalDate dataVencimento,

      CategoriaDespesa categoria,

      MetodoPagamento metodoPagamento,

      StatusDespesa status
) {
}
