package parser;

import exceptions.ParseException;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void parseNumberTest() {
        Expression n = new Parser("67").parse();
        assertEquals(new NumberExpression(67), n);
    }

    @Test
    void parseFunctionTest() {
        Expression f = new Parser("min(12, 55)").parse();
        Expression expected = new FunctionExpression(FunctionName.MIN, List.of(new NumberExpression(12), new NumberExpression(55)));
        assertEquals(expected, f);
    }

    @Test
    void parseParenthesesTest() {
        Expression e = new Parser("(2 + 3) * 4").parse();
        Expression expected = new BinaryExpression(
                BinaryOperator.MULTIPLY,
                new BinaryExpression(BinaryOperator.ADD,
                        new NumberExpression(2), new NumberExpression(3)),
                new NumberExpression(4)
        );
        assertEquals(expected, e);
    }

    @Test
    void unaryMinusBindsLooserThanPower() {
        Expression e = new Parser("-2^2").parse();
        Expression expected = new UnaryExpression(UnaryOperator.NEGATE,
                new BinaryExpression(BinaryOperator.POWER,
                        new NumberExpression(2), new NumberExpression(2))
        );
        assertEquals(expected, e);
    }

    @Test
    void powerIsRightAssociative() {
        Expression e = new Parser("2^3^2").parse();
        Expression expected = new BinaryExpression(BinaryOperator.POWER,
                new NumberExpression(2),
                new BinaryExpression(BinaryOperator.POWER,
                        new NumberExpression(3), new NumberExpression(2))
        );
        assertEquals(expected, e);
    }

    @Test
    void parseExceptionHasPosition() {
        var ex = assertThrows(ParseException.class,
                () -> new Parser("2 + (3 * x").parse());
        assertEquals(10, ex.getPosition());
    }

    @Test
    void unknownFunctionThrowTest() {
        var ex = assertThrows(ParseException.class,
                () -> new Parser("foo(1, 2)").parse());
        assertEquals(0, ex.getPosition());
    }

    @Test
    void emptyDot() {
        assertThrows(ParseException.class, () -> new Parser("1.").parse());
    }

    @Test
    void emptyInput() {
        assertThrows(ParseException.class, () -> new Parser("").parse());
    }
}