package model;

import exceptions.InvalidExpressionState;

import java.util.Objects;

/**
 * Узел дерева выражения, подставляющий переменную по имени.
 * @param name имя переменной;
 */

public record VariableExpression(String name) implements Expression {
    /**
     * Создаёт узел переменной с проверкой корректности имени.
     *
     * @param name имя переменной;
     * @throws InvalidExpressionState если {@code name == null};
     * @throws IllegalArgumentException если имя пустое или состоит только из пробелов;
     */
    public VariableExpression {
        if (name == null) {
            throw new InvalidExpressionState("Имя переменной не может быть == null.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя переменной не может быть пустым.");
        }
    }

    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения;
     */
    @Override
    public String format() {
        return name;
    }

    /**
     * Возвращает упрощённую версию данного выражения.
     *
     * @return упрощённое выражение;
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
