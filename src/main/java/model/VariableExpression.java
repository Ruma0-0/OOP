package model;

import java.util.Objects;

/**
 * Узел дерева выражения, подставляющий переменную по имени
 * @param name имя переменной
 */

public record VariableExpression(String name) implements Expression {
    public VariableExpression {

        /**
         * Создаёт узел переменной с проверкой корректности имени.
         *
         * @param name имя переменной
         * @throws NullPointerException     если {@code name == null}
         * @throws IllegalArgumentException если имя пустое или состоит только из пробелов
         */

        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя переменной не может быть пустым.");
        }
    }
}
