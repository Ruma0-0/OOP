package model;

public record UnaryExpression(UnaryOperator operator, Expression operand) implements Expression {
}
