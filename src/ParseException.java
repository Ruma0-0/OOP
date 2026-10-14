public class ParseException extends ExpressionException {
    private final int position;

    public ParseException(String message, int position) {
        super(message);
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }
}
