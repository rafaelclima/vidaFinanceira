package br.com.rafaellima.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.model.FonteReceita;
import br.com.rafaellima.demo.model.StatusReceita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ReceitaRequestDTO(

        @NotBlank(message = "O campo descricao é obrigatório") String descricao,

        @NotBlank(message = "O campo valor é obrigatório") @PositiveOrZero(message = "Valor tem que ser maior ou igual a 0") BigDecimal valor,

        @NotBlank(message = "O campo data de recebimento é obrigatório") LocalDate dataRecebimento,
        @NotBlank(message = "O campo status é obrigatório") StatusReceita status,
        @NotBlank(message = "O campo fonte da receita é obrigatório") FonteReceita fonte

) {

}
