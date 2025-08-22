package br.com.rafaellima.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistroDTO(

    @NotBlank(message = "O nome é obrigatório") String nome,

    @NotBlank(message = "O email é obrigatório") @Email(message = "Formato de email inválido") String email,

    @NotBlank(message = "A senha não pode ser vazia") @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$", message = "A senha deve conter letras e números") String senha

) {

}
