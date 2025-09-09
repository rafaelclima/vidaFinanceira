package br.com.rafaellima.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {
    // A classe do Enum que queremos validar
    Class<? extends Enum<?>> enumClass();

    // Mensagem de erro padrão
    String message() default "O valor fornecido não é válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
