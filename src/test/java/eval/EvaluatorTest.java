package eval;

import exceptions.DivisionByZeroException;
import exceptions.UnknownVariableException;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {
    private final Evaluator evaluator = new Evaluator();

    private double eval(String input, Map<String, Double> ctx) {
        return evaluator.evaluate(new Parser(input).parse(), ctx);
    }

    @Test
    void simpleArithmetic() {
        assertEquals(14.0, eval("2 + 3 * 4", Map.of()), 1e-9);
    }

    @Test
    void withVariables() {
        assertEquals(16.0, eval("x^2 + 2*x + 1", Map.of("x", 3.0)), 1e-9);
    }

    @Test
    void functions() {
        assertEquals(2.0, eval("min(2, 10)", Map.of()), 1e-9);
        assertEquals(10.0, eval("max(2, 10)", Map.of()), 1e-9);
        assertEquals(3.0, eval("abs(-3)", Map.of()), 1e-9);
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(DivisionByZeroException.class,
                () -> eval("1 / 0", Map.of()));
    }
}