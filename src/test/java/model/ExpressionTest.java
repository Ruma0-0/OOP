package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    @Test
    void numberEqualsAndHashCode() {
        var a = new NumberExpression(42);
        var b = new NumberExpression(42);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void variableEqualsAndHashCode() {
        assertEquals(new VariableExpression("x"), new VariableExpression("x"));
        assertEquals(new VariableExpression("x").hashCode(),
                new VariableExpression("x").hashCode());
    }

    @Test
    void differentNumbersNotEqual() {
        assertNotEquals(new NumberExpression(1), new NumberExpression(2));
    }

    @Test
    void functionCopiesArguments() {
        var mutable = new ArrayList<Expression>();
        mutable.add(new NumberExpression(1));
        var fn = new FunctionExpression(FunctionName.ABS, mutable);
        mutable.add(new NumberExpression(99));
        assertEquals(1, fn.arguments().size());
    }

    @Test
    void blankVariableNameThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new VariableExpression("   "));
    }
}