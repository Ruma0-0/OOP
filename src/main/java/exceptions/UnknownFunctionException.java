package exceptions;

public class UnknownFunctionException extends ExpressionException {
    private final int position;
    public UnknownFunctionException(String function) {
        super("Неизвестная функция " + function);
        position = 0;
    }
    public UnknownFunctionException(String function, int pos) {
        super("Неизвестная функция " + function + " в позиции" + pos);
        position = pos;
    }
    public int getPosition() {
        return position;
    }
}
