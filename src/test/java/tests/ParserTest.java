package tests;

import exceptions.ParseException;
import model.Expression;
import model.FunctionExpression;
import model.FunctionName;
import model.NumberExpression;
import org.junit.jupiter.api.Test;
import parser.Parser;

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
    void emptyDot() {
        assertThrows(ParseException.class, () -> new Parser("1.").parse());
    }

    @Test
    void emptyInput() {
        assertThrows(ParseException.class, () -> new Parser("").parse());
    }
}