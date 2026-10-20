package app;

import eval.Evaluator;
import derivator.Derivator;
import exceptions.DerivateException;
import exceptions.ExpressionException;
import exceptions.ParseException;
import model.*;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Expression> nodes = createNodes();
        System.out.println("Создано узлов модели: " + nodes.size());
        System.out.println();

        demo("2 + 3 * 4", Map.of());
        demo("(2 + 3) * 4", Map.of());
        demo("- 2 ^ 2", Map.of());
        demo("(-2) ^ 2", Map.of());
        demo("2 ^ 3 ^ 2", Map.of());
        demo("2 * -3", Map.of());
        demo("x ^ 2 + 2 * x + 1", Map.of("x", 2.0));
        demo("min(15, 3) + abs(-5)", Map.of());
        demo("max(10, 20) / 2", Map.of());
        demo("--3", Map.of());
        demo("-(3 + 5)", Map.of());
        demo("-3 + 5", Map.of());
        demo("3   + (2 + (5^1 + 2) + 3)", Map.of());
        demo("4^(-2)", Map.of());
        demo("x + 0 + x ^ 1 + x ^ 0 + x * 1 + x * 0", Map.of("x", 3.0));
        demo("2 * 3 + x", Map.of("x", 12.0));

        demo("1 / 0", Map.of());
        demo("y + 1", Map.of("x", 1.0));
        demo("2 + (3 * x", Map.of());
        demo("qqq(1, 2)", Map.of());
        demo("2.", Map.of());
        demo("", Map.of());
    }

    private static List<Expression> createNodes() {
        var nodes = new ArrayList<Expression>();
        for (int i = 1; i <= 5; i++) nodes.add(new NumberExpression(i));

        nodes.add(new NumberExpression(1.0));
        nodes.add(new NumberExpression(-3));
        nodes.add(new NumberExpression(0.5));
        nodes.add(new NumberExpression(3.14159));

        for (String v : List.of("x", "x1", "y", "z", "alpha", "beta")) nodes.add(new VariableExpression(v));

        nodes.add(new UnaryExpression(UnaryOperator.NEGATE, new NumberExpression(1)));
        nodes.add(new UnaryExpression(UnaryOperator.NEGATE, new VariableExpression("x")));
        nodes.add(new UnaryExpression(UnaryOperator.NEGATE, new UnaryExpression(UnaryOperator.NEGATE, new VariableExpression("x"))));

        for (var op : BinaryOperator.values()) {
            nodes.add(new BinaryExpression(op, new NumberExpression(1), new NumberExpression(2)));
        }

        nodes.add(new FunctionExpression(FunctionName.MIN, List.of(new NumberExpression(1), new NumberExpression(2))));

        nodes.add(new FunctionExpression(FunctionName.MAX, List.of(new NumberExpression(1), new NumberExpression(2))));

        nodes.add(new FunctionExpression(FunctionName.ABS, List.of(new NumberExpression(-5))));

        nodes.add(new BinaryExpression(BinaryOperator.ADD, new VariableExpression("x"), new BinaryExpression(BinaryOperator.MULTIPLY, new NumberExpression(2), new VariableExpression("y"))));
        nodes.add(new BinaryExpression(BinaryOperator.POWER, new VariableExpression("x"), new NumberExpression(2)));
        nodes.add(new UnaryExpression(UnaryOperator.NEGATE, new BinaryExpression(BinaryOperator.ADD, new NumberExpression(1), new NumberExpression(2))));

        nodes.add(new FunctionExpression(FunctionName.MIN, List.of(new NumberExpression(21), new FunctionExpression(FunctionName.ABS, List.of(new NumberExpression(-30))))));

        return nodes;
    }
    private static void demo(String input, Map<String, Double> context) {
        System.out.println("Вход: \\" + input + "\\");
        try {
            var tree = new Parser(input).parse();
            System.out.println("Дерево: " + tree);
            var value = new Evaluator().evaluate(tree, context);
            System.out.println("Значение: " + value);
            System.out.println("Строковое представление: " + tree.format());
            System.out.println("Свертка констант: " + tree.simplify().format());
            var derivator = new Derivator().derivate(tree, "x");
            System.out.println("Посимвольное дифференцирование: " + derivator.simplify().format());
        } catch (ParseException e) {
            System.out.println("Ошибка разбора: " + e.getMessage());
            System.out.println(input);
            System.out.println(" ".repeat(Math.max(0, e.getPosition())) + "^");
        } catch(DerivateException e){
            System.out.println("Ошибка дифференцирвания: " + e.getMessage());
        } catch (ExpressionException e) {
            System.out.println("Ошибка вычисления: " + e.getMessage());
        }
        System.out.println();
    }
}
