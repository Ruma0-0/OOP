package exceptions;

public class UnknownVariableException extends ExpressionException {
    public UnknownVariableException(String name) {
        super("Неизвестное имя переменной " + name);
    }
}
