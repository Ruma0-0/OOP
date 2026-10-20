package model;

/**
 * Узел дерева выражения, представляющий унарную операцию над одним операндом.
 *
 * @param operator операция;
 * @param operand операнд;
 */
public record UnaryExpression(UnaryOperator operator, Expression operand) implements Expression {
    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения;
     */
    @Override
    public String format() {
        var text = operand.format();

        if (operand instanceof BinaryExpression binary
                && (binary.operator() == BinaryOperator.ADD
                || binary.operator() == BinaryOperator.SUBTRACT)) {
            return operator.getSymbol() + "(" + text + ")";
        }
        return operator.getSymbol() + text;
    }

    /**
     * Возвращает упрощённую версию данного выражения.
     *
     * @return упрощённое выражение;
     */
    @Override
    public Expression simplify() {
        var inner = operand.simplify();
        if (inner instanceof NumberExpression) {
            return new NumberExpression(-((NumberExpression) inner).value());
        }
        return new UnaryExpression(operator, inner);
    }
}
