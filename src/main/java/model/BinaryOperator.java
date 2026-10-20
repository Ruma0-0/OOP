package model;

import exceptions.DivisionByZeroException;

/**
 * Бинарные операторы с приоритетом, ассоциативностью и реализацией вычисления.
 */
public enum BinaryOperator {
    /**Сложение.*/
    ADD(3, Associativity.LEFT, "+") {
        @Override
        public double calculate(double a, double b) {
            return a + b;
        }
    },
    /**Вычитание.*/
    SUBTRACT(3, Associativity.LEFT, "-") {
        @Override
        public double calculate(double a, double b) {
            return a - b;
        }
    },
    /**Умножение.*/
    MULTIPLY(2, Associativity.LEFT, "*") {
        @Override
        public double calculate(double a, double b) {
            return a * b;
        }
    },
    /**Деление. Бросает {@link DivisionByZeroException} при {@code b == 0}.*/
    DIVIDE(2, Associativity.LEFT, "/") {
        @Override
        public double calculate(double a, double b) {
            if (b == 0) {
                throw new DivisionByZeroException();
            }
            return a / b;
        }
    },
    /**Возведение в степень.*/
    POWER(1, Associativity.RIGHT, "^") {
        @Override
        public double calculate(double a, double b) {
            return Math.pow(a, b);
        }
    };

    private final int priority;
    private final Associativity associativity;
    private final String symbol;

    BinaryOperator(int priority, Associativity associativity, String symbol) {
        this.priority = priority;
        this.associativity = associativity;
        this.symbol = symbol;
    }

    /**
     * Вычисляет результат применения оператора.
     *
     * @param a левый операнд
     * @param b правый операнд
     * @return результат операции
     * @throws DivisionByZeroException если оператор — деление, а {@code b == 0}
     */
    public abstract double calculate(double a, double b);

    /**
     * @return приоритет оператора (меньше — выше приоритет)
     */
    public int getPriority() {
        return priority;
    }
    /**
     * @return ассоциативность оператора
     */
    public Associativity getAssociativity() {
        return associativity;
    }

    /**
     * @return символ арифметической операции
     */
    public String getSymbol() {return symbol;}
}
