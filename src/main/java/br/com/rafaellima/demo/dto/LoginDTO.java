package br.com.rafaellima.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "É necessário passar um email.") @Email(message = "Passse um email válido 'email@dominio.com'") String email,

    @NotBlank(message = "Senha é obrigatória") String senha) {

}
