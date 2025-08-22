package br.com.rafaellima.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.model.FonteReceita;
import br.com.rafaellima.demo.model.StatusReceita;
import jakarta.validation.constraints.NotNull;

public record ReceitaRequestDTO(

    @NotNull(message = "O campo descricao é obrigatório") String descricao,
    @NotNull(message = "O campo valor é obrigatório") BigDecimal valor,
    @NotNull(message = "O campo data de recebimento é obrigadtório") LocalDate dataRecebimento,
    @NotNull(message = "O campo status é obrigatório") StatusReceita status,
    @NotNull(message = "O campo fonte da receita é obrigatório") FonteReceita fonte

) {

}
