package model;

import exceptions.DivisionByZeroException;
import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.*;

class SimplifyTest {
    @Test
    void simplifyConstantFolding() {
        assertEquals("6.0 + x", new Parser("2 * 3 + x").parse().simplify().format());
    }

    @Test
    void simplifyMultiplyByOne() {
        assertEquals("x", new Parser("x * 1").parse().simplify().format());
    }

    @Test
    void simplifyAddZero() {
        assertEquals("x", new Parser("x + 0").parse().simplify().format());
    }

    @Test
    void simplifyMultiplyByZero() {
        assertEquals("0.0", new Parser("x * 0").parse().simplify().format());
    }

    @Test
    void simplifyPowerZero() {
        assertEquals("1.0", new Parser("x ^ 0").parse().simplify().format());
    }

    @Test
    void simplifyDoesNotReturnNull() {
        assertNotNull(new Parser("x + y").parse().simplify());
    }

    @Test
    void simplifyUnaryConstant() {
        assertEquals("-5.0", new Parser("-5").parse().simplify().format());
    }

    @Test
    void simplifyFunctionConstants() {
        assertEquals("1.0", new Parser("min(3, 1)").parse().simplify().format());
    }

    @Test
    void simplifyDivisionByZeroThrows() {
        assertThrows(DivisionByZeroException.class,
                () -> new Parser("1 / 0").parse().simplify());
    }
}