package model;

import org.junit.jupiter.api.Test;
import parser.Parser;

import static org.junit.jupiter.api.Assertions.*;

class FormatTest {
    private String format(String input) {
        return new Parser(input).parse().format();
    }

    @Test
    void formatsNumber() {
        assertEquals("42.0", format("42"));
    }

    @Test
    void formatsVariable() {
        assertEquals("x", format("x"));
    }

    @Test
    void formatsFunction() {
        assertEquals("min(1.0, 2.0)", format("min(1, 2)"));
        assertEquals("abs(-3.0)", format("abs(-3)"));
    }


    @Test
    void noParensForHigherPrecedence() {
        assertEquals("2.0 + 3.0 * 4.0", format("2 + 3 * 4"));
    }

    @Test
    void parensForLowerPrecedence() {
        assertEquals("(2.0 + 3.0) * 4.0", format("(2 + 3) * 4"));
    }

    @Test
    void noParensForLeftAssociativeRightChild() {
        assertEquals("2.0 - 3.0 - 4.0", format("2 - 3 - 4"));
    }

    @Test
    void parensForRightChildOfLeftAssociative() {
        assertEquals("2.0 - (3.0 - 4.0)", format("2 - (3 - 4)"));
    }

    @Test
    void noParensForRightAssociativeRightChild() {
        assertEquals("2.0 ^ 3.0 ^ 2.0", format("2 ^ 3 ^ 2"));
    }

    @Test
    void parensForLeftChildOfRightAssociative() {

        assertEquals("(2.0 ^ 3.0) ^ 2.0", format("(2 ^ 3) ^ 2"));
    }

    @Test
    void noParensForNegateInSum() {
        assertEquals("-3.0 + 5.0", format("-3 + 5"));
    }

    @Test
    void noParensForNegateInProduct() {
        assertEquals("-3.0 * 5.0", format("-3 * 5"));
    }

    @Test
    void parensForNegateAsLeftChildOfPower() {
        assertEquals("(-3.0) ^ 2.0", format("(-3) ^ 2"));
    }

    @Test
    void noParensForNegateAsRightChildOfPower() {
        assertEquals("2.0 ^ -3.0", format("2 ^ -3"));
    }

    @Test
    void parensForNegateOfSum() {
        assertEquals("-(2.0 + 3.0)", format("-(2 + 3)"));
    }

    @Test
    void noParensForNegateOfProduct() {
        assertEquals("-2.0 * 3.0", format("-(2 * 3)"));
    }

    @Test
    void noParensForNegateOfPower() {
        assertEquals("-2.0 ^ 2.0", format("-2 ^ 2"));
    }

    @Test
    void doesNotAddRedundantParensInSum() {
        assertEquals("1.0 + 2.0 + 3.0", format("1 + 2 + 3"));
    }

    @Test
    void doesNotAddRedundantParensInProduct() {
        assertEquals("1.0 * 2.0 * 3.0", format("1 * 2 * 3"));
    }

    @Test
    void doesNotAddParensAroundFunction() {
        assertEquals("min(1.0, 2.0) + 3.0", format("min(1, 2) + 3"));
        assertEquals("min(1.0, 2.0) * 3.0", format("min(1, 2) * 3"));
    }

    @Test
    void doesNotAddParensAroundVariable() {
        assertEquals("x * y", format("x * y"));
        assertEquals("x + y", format("x + y"));
    }

    @Test
    void formatsNestedExpression() {
        assertEquals("(1.0 + 2.0) * (3.0 + 4.0)", format("(1 + 2) * (3 + 4)"));
    }

    @Test
    void formatsNestedPower() {
        assertEquals("((2.0 ^ 3.0) ^ 2.0) ^ 2.0", format("((2 ^ 3) ^ 2) ^ 2"));
    }
}