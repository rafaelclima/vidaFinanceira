package br.com.rafaellima.demo.dto;

import br.com.rafaellima.demo.model.CategoriaDespesa;
import br.com.rafaellima.demo.model.MetodoPagamento;
import br.com.rafaellima.demo.model.StatusDespesa;
import br.com.rafaellima.demo.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaRequestDTO(
      @NotBlank(message = "O campo descrição é obrigatório")
      String descricao,

      @NotNull(message = "O campo valor é obrigatório")
      @Positive(message = "O valor tem que ser maior que 0")
      BigDecimal valor,

      @NotNull(message = "O campo data de despesa é obrigatório")
      @PastOrPresent(message = "A data de despesa não pode ser futura")
      LocalDate dataDespesa,

      LocalDate dataVencimento,

      @EnumValue(enumClass = CategoriaDespesa.class, message = "Categoria inválida.")
      @NotBlank(message = "O campo categoria é obrigatório")
      String categoria,

      @EnumValue(enumClass = MetodoPagamento.class, message = "Método de pagamento inválido.")
      @NotBlank(message = "O campo método de pagamento é obrigatório")
      String metodoPagamento,

      @EnumValue(enumClass = StatusDespesa.class, message = "Status inválido.")
      @NotBlank(message = "O campo status é obrigatório")
      String status
) {
}
