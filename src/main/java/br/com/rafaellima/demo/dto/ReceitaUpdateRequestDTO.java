package br.com.rafaellima.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.rafaellima.demo.model.FonteReceita;
import br.com.rafaellima.demo.model.StatusReceita;

public record ReceitaUpdateRequestDTO(

    String descricao,
    BigDecimal valor,
    LocalDate dataRecebimento,
    StatusReceita status,
    FonteReceita fonte

) {

}
