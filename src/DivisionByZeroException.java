public class DivisionByZeroException extends ExpressionException {
    public DivisionByZeroException(int position) {
        super("Деление на ноль в позиции " + position);
    }
}
