package model;

import exceptions.DivisionByZeroException;

public enum BinaryOperator {
    ADD(3, Associativity.LEFT) {
        @Override
        public double calculate(double a, double b) {
            return a + b;
        }
    },
    SUBTRACT(3, Associativity.LEFT) {
        @Override
        public double calculate(double a, double b) {
            return a - b;
        }
    },
    MULTIPLY(2, Associativity.LEFT) {
        @Override
        public double calculate(double a, double b) {
            return a * b;
        }
    },

    DIVIDE(2, Associativity.LEFT) {
        @Override
        public double calculate(double a, double b) {
            if (b == 0) {
                throw new DivisionByZeroException();
            }
            return a / b;
        }
    },
    POWER(1, Associativity.RIGHT) {
        @Override
        public double calculate(double a, double b) {
            return Math.pow(a, b);
        }
    };

    private final int priority;
    private final Associativity associativity;

    BinaryOperator(int priority, Associativity associativity) {
        this.priority = priority;
        this.associativity = associativity;
    }
    public abstract double calculate(double a, double b);

    public int getPriority() {
        return priority;
    }

    public Associativity getAssociativity() {
        return associativity;
    }
}
