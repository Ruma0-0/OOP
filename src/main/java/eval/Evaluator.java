package eval;

import exceptions.UnknownVariableException;
import model.*;

import java.util.Map;

public class Evaluator {
    /**
     * Вычисление синтаксического дерева выражения.
     * @param expression вычисляемое выражение;
     * @param context контекст переменных;
     * @return значение выражения;
     * @throws UnknownVariableException если встречается переменная, не заданная в контексте;
     */
    public double evaluate(Expression expression, Map<String, Double> context) {
        return switch (expression) {
            case NumberExpression n -> n.value();
            case VariableExpression v -> {
                var value = context.get(v.name());
                if (value == null) {
                    throw new UnknownVariableException(v.name());
                }
                yield value;
            }
            case UnaryExpression u -> switch(u.operator()){
                case NEGATE -> -evaluate(u.operand(), context);
            };
            case BinaryExpression b -> {
                double left = evaluate(b.left(), context);
                double right = evaluate(b.right(), context);
                yield b.operator().calculate(left, right);
            }
            case FunctionExpression f -> evaluateFunction(f, context);
        };
    }
    private double evaluateFunction(FunctionExpression function, Map<String, Double> context) {
        var args = function.arguments().stream().map(a -> evaluate(a, context)).toList();

        return switch (function.function()) {
            case MIN -> args.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            case MAX -> args.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
            case ABS -> Math.abs(args.getFirst());
        };
    }
}
