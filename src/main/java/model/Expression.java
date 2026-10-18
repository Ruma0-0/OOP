package model;

/**
 * Синтаксическое дерево выражения. Реализации — records,
 * покрытые sealed-иерархией.
 */

sealed public interface Expression
        permits NumberExpression, VariableExpression,
        UnaryExpression, BinaryExpression, FunctionExpression {
}
