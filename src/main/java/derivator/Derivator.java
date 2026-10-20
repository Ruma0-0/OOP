package derivator;

import exceptions.DerivateException;
import exceptions.ExpressionException;
import model.*;

import java.util.List;

public class Derivator {
    /**
     * Нахождение производной выражения по заданной переменной.
     * @param expression дифференцируемое выражение;
     * @param variable переменная, по которой проводится дифференцирование;
     * @return производная исходного выражения;
     * @throws DerivateException если встречаются выражения, которые программа неспособна продифференцировать;
     */
    public Expression derivate(Expression expression, String variable) {
        return switch(expression) {
            case NumberExpression n -> new NumberExpression(0);
            case VariableExpression v -> v.name().equals(variable) ? new NumberExpression(1) : new NumberExpression(0);
            case BinaryExpression b -> derivateBinary(b, variable);
            case FunctionExpression f -> derivateFunction(f, variable);
            case UnaryExpression u -> new UnaryExpression(u.operator(), derivate(u.operand(), variable));
        };
    }

    private Expression derivateBinary(BinaryExpression binary, String variable) {
        var u = binary.left();
        var v = binary.right();
        var du = derivate(u, variable);
        var dv = derivate(v, variable);

        return switch (binary.operator()) {
            case ADD, SUBTRACT -> new BinaryExpression(binary.operator(), du, dv);
            case MULTIPLY -> new BinaryExpression(BinaryOperator.ADD,
                    new BinaryExpression(BinaryOperator.MULTIPLY, du, v),
                    new BinaryExpression(BinaryOperator.MULTIPLY, u, dv));
            case DIVIDE -> new BinaryExpression(BinaryOperator.DIVIDE,
                    new BinaryExpression(BinaryOperator.SUBTRACT,
                            new BinaryExpression(BinaryOperator.MULTIPLY, du, v),
                            new BinaryExpression(BinaryOperator.MULTIPLY, u, dv)),
                    new BinaryExpression(BinaryOperator.POWER, v, new NumberExpression(2)));
            case POWER -> derivatePower(u, du, v, dv, variable);
        };
    }

    private Expression derivatePower(Expression u, Expression du, Expression v, Expression dv, String variable) {
        if (!dependsOn(v, variable)) {
            return new BinaryExpression(BinaryOperator.MULTIPLY, du,
                    new BinaryExpression(BinaryOperator.MULTIPLY, v,
                            new BinaryExpression(
                                    BinaryOperator.POWER, u,
                                    new BinaryExpression(BinaryOperator.SUBTRACT, v, new NumberExpression(1))
                            )
                    )
            );
        }

        throw new DerivateException("Дифференцирование показательных функций не поддерживается");
    }

    private Expression derivateFunction(FunctionExpression f, String variable) {
        return switch (f.function()) {
            case ABS -> {
                var u = f.arguments().getFirst();
                var du = derivate(u, variable);
                yield new BinaryExpression(BinaryOperator.MULTIPLY, du, new BinaryExpression(BinaryOperator.DIVIDE, new VariableExpression(variable), new FunctionExpression(FunctionName.ABS, List.of(u))));
            }
            case MIN, MAX -> throw new DerivateException("Дифференцирование функции " + f.function().getName() + " не поддерживается.");
        };
    }

    private boolean dependsOn(Expression expression, String variable) {
        return switch (expression) {
            case NumberExpression n -> false;
            case VariableExpression v -> v.name().equals(variable);
            case UnaryExpression u -> dependsOn(u.operand(), variable);
            case BinaryExpression b -> dependsOn(b.left(), variable) || dependsOn(b.right(), variable);
            case FunctionExpression f -> throw new DerivateException("Дифференцирование функции " + f.function().getName() + " не поддерживается.");
        };
    }
}
