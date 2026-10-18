package model;

/**
 * Узел дерева выражения.
 *
 */

sealed public interface Expression
        permits NumberExpression, VariableExpression,
        UnaryExpression, BinaryExpression, FunctionExpression {
}
