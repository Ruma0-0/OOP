package model;

import java.util.Objects;

public record VariableExpression(String name) implements Expression {
    public VariableExpression {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя переменной не может быть пустым.");
        }
    }
}
