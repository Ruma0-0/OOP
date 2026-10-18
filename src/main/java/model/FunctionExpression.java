package model;

import java.util.List;

/**
 * Вызов функции.
 *
 * @param function  имя функции
 * @param arguments список аргументов (неизменяемый)
 */
public record FunctionExpression(FunctionName function, List<Expression> arguments) implements Expression {
    /**
     * @param function  имя функции
     * @param arguments аргументы
     * @throws NullPointerException если {@code function} или {@code arguments} == null
     */
    public FunctionExpression {
        arguments = List.copyOf(arguments);
    }
}
