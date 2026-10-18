package model;

/**
 * Бинарное выражение вида {@code left operator right}.
 * @param operator оператор
 * @param left левый операнд
 * @param right правый операнд
 */
public record BinaryExpression(BinaryOperator operator, Expression left, Expression right) implements Expression {
}
