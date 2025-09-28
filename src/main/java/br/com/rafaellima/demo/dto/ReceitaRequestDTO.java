package br.com.rafaellima.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.model.FonteReceita;
import br.com.rafaellima.demo.model.StatusReceita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ReceitaRequestDTO(

        @NotBlank(message = "O campo descricao é obrigatório") String descricao,

        @NotNull(message = "O campo valor é obrigatório") @PositiveOrZero(message = "Valor tem que ser maior ou igual a 0") BigDecimal valor,

        @NotNull(message = "O campo data de recebimento é obrigatório") LocalDate dataRecebimento,
        @NotNull(message = "O campo status é obrigatório") StatusReceita status,
        @NotNull(message = "O campo fonte da receita é obrigatório") FonteReceita fonte

) {

}
