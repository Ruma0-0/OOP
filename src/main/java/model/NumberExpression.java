package model;
/**
 * Узел дерева выражения, представляющий числовую константу.
 * @param value значение числа;
 */
public record NumberExpression(double value) implements Expression{
    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения;
     */
    @Override
    public String format() {
        return String.valueOf(value);
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
