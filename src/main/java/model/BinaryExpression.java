package model;

import exceptions.DivisionByZeroException;

/**
 * Бинарное выражение вида {@code left operator right}.
 * @param operator оператор
 * @param left левый операнд
 * @param right правый операнд
 */
public record BinaryExpression(BinaryOperator operator, Expression left, Expression right) implements Expression {
    /**
     * Возвращает строковое представление выражения
     *
     * @return строковое представление выражения
     */
    @Override
    public String format() {
        var leftString = formatChild(left, operator, false);
        var rightString = formatChild(right, operator, true);

        return leftString + " " + operator.getSymbol() + " " + rightString;
    }

    /**
     * Возвращает упрощённую версию данного выражения.
     *
     * @return упрощённое выражение;
     */
    @Override
    public Expression simplify() {
        var lefts = left.simplify();
        var rights = right.simplify();

        if (lefts instanceof NumberExpression lN && rights instanceof NumberExpression rN) {
            return new NumberExpression(operator.calculate(lN.value(), rN.value()));
        }
        if (rights instanceof NumberExpression rN) {
            if (operator == BinaryOperator.ADD && rN.value() == 0) {return lefts;}
            if (operator == BinaryOperator.SUBTRACT && rN.value() == 0) {return lefts;}
            if (operator == BinaryOperator.MULTIPLY && rN.value() == 0) {return new NumberExpression(0);}
            if (operator == BinaryOperator.MULTIPLY && rN.value() == 1) {return lefts;}
            if (operator == BinaryOperator.DIVIDE && rN.value() == 1) {return lefts;}
            if (operator == BinaryOperator.DIVIDE && rN.value() == 0) {throw new DivisionByZeroException();}
            if (operator == BinaryOperator.POWER && rN.value() == 0) {return new NumberExpression(1);}
            if (operator == BinaryOperator.POWER && rN.value() == 1) {return lefts;}
        }
        if (lefts instanceof NumberExpression lN) {
            if (operator == BinaryOperator.DIVIDE && lN.value() == 0) {return new NumberExpression(0);}
        }
        return new BinaryExpression(operator, lefts, rights);
    }
}
