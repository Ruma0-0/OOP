package parser;

import exceptions.ParseException;
import model.*;

public class Parser {
    private String input;
    private int position;

    public Parser(String input) {
        this.input = input;
        this.position = 0;
    }
    public Expression parse() {
        var result = parseAdditive();
        skipSpaces();

        if (position != input.length()) {
            throw new ParseException("Ожидался конец выражения.", position);
        }

        return result;
    }

    private Expression parseAdditive() {
        var left = parseMultiplicative();

        while (true) {
            skipSpaces();

            if (peek() == '+') {
                consume('+');
                var right = parseMultiplicative();

                left = new BinaryExpression(BinaryOperator.ADD, left, right);
            } else if (peek() == '-') {
                consume('-');
                var right = parseMultiplicative();

                left = new BinaryExpression(BinaryOperator.SUBTRACT, left, right);
            } else {
                return left;
            }
        }
    }

    private Expression parseMultiplicative() {
        var left = parsePrimary();
        while (true) {
            skipSpaces();

            if (peek() == '*') {
                consume('*');
                var right = parsePrimary();

                left = new BinaryExpression(BinaryOperator.MULTIPLY, left, right);
            } else if (peek() == '/') {
                consume('/');
                var right = parsePrimary();

                left = new BinaryExpression(BinaryOperator.DIVIDE, left, right);
            } else {
                return left;
            }
        }
    }

    private Expression parsePrimary() {
        skipSpaces();

        if (position >= input.length()) {
            throw new ParseException("Ожидалось выражение", position);
        }

        char current = input.charAt(position);
        if (current == '(') {
            consume('(');

            var expression = parseAdditive();

            skipSpaces();
            if (position >= input.length()) {
                throw new ParseException("Ожидалась закрывающая скобка", position);
            }

            consume(')');

            return expression;
        }

        if (Character.isDigit(current)) {
            return parseNumber();
        }
        if (Character.isLetter(current)) {
            return parseIdentifier();
        }

        throw new ParseException("Неожиданный символ " + current, position);
    }

    private Expression parseNumber() {
        int start = position;

        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position++;
        }

        if (position < input.length()
                && input.charAt(position) == '.') {
            position++;

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {
                position++;
            }
        }

        var text = input.substring(start, position);

        return new NumberExpression(Double.parseDouble(text));
    }

    private Expression parseIdentifier() {
        int start = position;

        while (position < input.length() && Character.isLetterOrDigit(input.charAt(position))) {
            position++;
        }

        var name = input.substring(start, position);

        return new VariableExpression(name);
    }

    private void skipSpaces() {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }

    private void consume(char expected) {
        if(position >= input.length() || input.charAt(position) == expected) {
            throw new ParseException("Ожидался символ: " + expected, position);
        }

        position++;
    }

    private char peek() {
        if (position < input.length()) {
            return input.charAt(position);
        } else {
            return '\0';
        }
    }
}
