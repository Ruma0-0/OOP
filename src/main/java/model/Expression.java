package model;

/**
 * Синтаксическое дерево выражения. Реализации — records,
 * покрытые sealed-иерархией.
 */

sealed public interface Expression
        permits NumberExpression, VariableExpression,
        UnaryExpression, BinaryExpression, FunctionExpression {
    /**
     * Возвращает строковое представление выражения
     *
     * @return строковое представление выражения
     */
    String format();

    /**
     * Возвращает упрощённую версию данного выражения.
     *
     * @return упрощённое выражение;
     */
    Expression simplify();


    /**
     * Форматирует дочернее выражение
     *
     * @param child дочернее выражение
     * @param parent оператор родителя
     * @param isRightChild является ли дочерняя операция правым операндом родителя
     *
     * @return строковое представление дочернего выражения с минимальным количеством необходимых скобок
     */
    default String formatChild(Expression child, BinaryOperator parent, boolean isRightChild) {
        var text = child.format();
        return switch (child) {
            case BinaryExpression b -> needsParentheses(b.operator(), parent, isRightChild) ? "(" + text + ")" : text;
            case UnaryExpression u -> needsParentheses(u.operator(), parent, isRightChild) ? "(" + text + ")" : text;
            case NumberExpression n -> text;
            case VariableExpression v -> text;
            case FunctionExpression f -> text;
        };
    }

    /**
     * Определяет, нужны ли скобки вокруг дочерней бинарной операции.
     *
     * @param child оператор потомка
     * @param parent оператор родителя
     * @param isRightChild является ли дочерняя операция правым операндом родителя
     *
     * @return нужно ли печатать скобки вокруг дочерней операции
     */
    default boolean needsParentheses(BinaryOperator child, BinaryOperator parent, boolean isRightChild) {
        if (parent == null) {return false;}

        if (child.getPriority() > parent.getPriority()) {return true;}

        if (child.getPriority() < parent.getPriority()) {return false;}

        if (parent == BinaryOperator.ADD || parent == BinaryOperator.MULTIPLY) {return false;}

        if (parent.getAssociativity() == Associativity.LEFT) {return isRightChild;}

        return !isRightChild;
    }

    default boolean needsParentheses(UnaryOperator child, BinaryOperator parent, boolean isRightChild) {
        if (parent == null) {return false;}

        return !isRightChild && parent == BinaryOperator.POWER;
    }
}
