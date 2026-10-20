package derivator;

import exceptions.DerivateException;
import org.junit.jupiter.api.Test;
import parser.Parser;
import model.Expression;

import static org.junit.jupiter.api.Assertions.*;

class DerivatorTest {
    private final Derivator derivator = new Derivator();

    private Expression derive(String input, String variable) {
        return derivator.derivate(new Parser(input).parse(), variable).simplify();
    }

    @Test
    void derivativeOfConstantIsZero() {
        assertEquals("0.0", derive("5", "x").format());
    }

    @Test
    void derivativeOfVariableIsOne() {
        assertEquals("1.0", derive("x", "x").format());
    }

    @Test
    void derivativeOfOtherVariableIsZero() {
        assertEquals("0.0", derive("y", "x").format());
    }

    @Test
    void derivativeOfSum() {
        assertEquals("2.0", derive("x + x", "x").format());
    }

    @Test
    void derivativeOfDifference() {
        assertEquals("0.0", derive("x - x", "x").format());
    }

    @Test
    void derivativeOfPolynomial() {
        assertEquals("2.0 * x + 2.0", derive("x^2 + 2*x + 1", "x").format());
    }


    @Test
    void derivativeOfProduct() {
        assertEquals("x + x", derive("x * x", "x").format());
    }

    @Test
    void derivativeOfDivision() {
        assertEquals("(x - x) / x ^ 2.0", derive("x / x", "x").format());
    }


    @Test
    void derivativeOfSquare() {
        assertEquals("2.0 * x", derive("x^2", "x").format());
    }

    @Test
    void derivativeOfCube() {
        assertEquals("3.0 * x ^ 2.0", derive("x^3", "x").format());
    }

    @Test
    void derivativeOfPowerOne() {
        assertEquals("1.0", derive("x^1", "x").format());
    }

    @Test
    void derivativeOfPowerZero() {
        assertEquals("0.0", derive("x^0", "x").format());
    }

    @Test
    void derivativeOfNegate() {
        assertEquals("-1.0", derive("-x", "x").format());
    }

    @Test
    void derivativeOfDoubleNegate() {
        assertEquals("1.0", derive("-(-x)", "x").format());
    }

    @Test
    void derivativeOfNestedPower() {
        var d = derive("(x + 1)^2", "x");
        var value = new eval.Evaluator().evaluate(d, java.util.Map.of("x", 3.0));
        assertEquals(8.0, value, 1e-9);
    }

    @Test
    void derivativeOfAbs() {
        var d = derive("abs(x)", "x");
        var evaluator = new eval.Evaluator();
        assertEquals(1.0, evaluator.evaluate(d, java.util.Map.of("x", 3.0)), 1e-9);
        assertEquals(-1.0, evaluator.evaluate(d, java.util.Map.of("x", -3.0)), 1e-9);
    }


    @Test
    void throwsForMinFunction() {
        var tree = new Parser("min(x, 1)").parse();
        assertThrows(DerivateException.class, () -> derivator.derivate(tree, "x"));
    }

    @Test
    void throwsForMaxFunction() {
        var tree = new Parser("max(x, 1)").parse();
        assertThrows(DerivateException.class, () -> derivator.derivate(tree, "x"));
    }

    @Test
    void throwsForVariableExponent() {
        var tree = new Parser("x ^ y").parse();
        assertThrows(DerivateException.class, () -> derivator.derivate(tree, "x"));
    }

}