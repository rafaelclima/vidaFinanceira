package br.com.rafaellima.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Set<String> acceptedValues;

    @Override
    public void initialize(EnumValue annotation) {
        // Pega todos os nomes dos valores do Enum e guarda em um Set para performance
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            // A validação de nulo deve ser feita por @NotNull ou @NotBlank
            return true;
        }
        // Retorna true se o valor da String estiver no nosso Set de valores aceitos
        return acceptedValues.contains(value);
    }
}
