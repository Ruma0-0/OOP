sealed public interface Expression
        permits NumberExpression, VariableExpression,
        UnaryExpression, BinaryExpression, FunctionExpression {
}
