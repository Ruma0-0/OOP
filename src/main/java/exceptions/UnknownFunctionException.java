package exceptions;

public class UnknownFunctionException extends ExpressionException {
    public UnknownFunctionException(String function) {
        super("Неизвестная функция " + function);
    }
    public UnknownFunctionException(String function, int pos) {super("Неизвестная функция " + function + " в позиции" + pos);}
}
