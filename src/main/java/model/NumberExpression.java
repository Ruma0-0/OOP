package model;
/**
 * Узел дерева выражения, представляющий числовую константу.
 * @param value значение числа
 */
public record NumberExpression(double value) implements Expression{
}
