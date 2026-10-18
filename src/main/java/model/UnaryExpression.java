package model;

/**
 * Узел дерева выражения, представляющий унарную операцию над одним операндом.
 *
 * @param operator операция
 * @param operand операнд
 */
public record UnaryExpression(UnaryOperator operator, Expression operand) implements Expression {
}
