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
            return a / b;
        }
    };

    BinaryOperator(int priority, Associativity associativity) {
    }
    abstract double calculate(double a, double b);
}
