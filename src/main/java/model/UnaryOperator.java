package model;

/**
 * Унарные операторы.
 */
public enum UnaryOperator {
    /** Смена знака.*/
    NEGATE( "-");

    private final String symbol;
    UnaryOperator(String c) {
        symbol = c;
    }
    public String getSymbol() {return symbol;}
}
