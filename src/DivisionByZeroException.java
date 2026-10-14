public class DivisionByZeroException extends ExpressionException {
    public DivisionByZeroException() {
        super("Обнаружено деление на ноль.");
    }
}
