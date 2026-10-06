import java.util.List;

public record FunctionExpression(FunctionName function, List<Expression> arguments) implements Expression {
    public FunctionExpression {
        arguments = List.copyOf(arguments);
    }
}
