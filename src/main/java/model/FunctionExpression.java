package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(function);
        arguments = List.copyOf(arguments);
    }

    /**
     * Возвращает строковое представление выражения
     *
     * @return строковое представление выражения
     */
    @Override
    public String format() {
        var result = new StringBuilder();

        result.append(function.getName());
        result.append("(");

        for (int i = 0; i < arguments.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(arguments.get(i).format());
        }

        result.append(")");
        return result.toString();
    }

    /**
     * Возвращает упрощённую версию данного выражения.
     *
     * @return упрощённое выражение;
     */
    @Override
    public Expression simplify() {
        var simplifiedArgs = new ArrayList<Expression>(arguments.size());
        for (var arg : arguments) {
            simplifiedArgs.add(arg.simplify());
        }

        boolean allNumbers = true;
        for (var arg : simplifiedArgs) {
            if (!(arg instanceof NumberExpression)) {
                allNumbers = false;
                break;
            }
        }

        if (allNumbers) {
            double result;
            switch (function) {
                case MIN -> {
                    double min = Double.POSITIVE_INFINITY;
                    for (var arg : simplifiedArgs) {
                        var value = ((NumberExpression) arg).value();
                        if (value < min) min = value;
                    }
                    result = min;
                }
                case MAX -> {
                    double max = Double.NEGATIVE_INFINITY;
                    for (var arg : simplifiedArgs) {
                        var value = ((NumberExpression) arg).value();
                        if (value > max) max = value;
                    }
                    result = max;
                }
                case ABS -> {
                    result = Math.abs(((NumberExpression) simplifiedArgs.getFirst()).value());
                }
                default -> throw new IllegalStateException("Неизвестная функция: " + function);
            }
            return new NumberExpression(result);
        }

        return new FunctionExpression(function, simplifiedArgs);
    }
}
