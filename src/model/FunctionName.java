package model;

import exceptions.ExpressionException;
import exceptions.ParseException;
import exceptions.UnknownVariableException;

public enum FunctionName {
    MIN("min", 2),
    MAX("max", 2),
    ABS("abs", 1);

    private final String text;
    private final int argumentCount;

    FunctionName(String text, int argumentCount) {
        this.text = text;
        this.argumentCount = argumentCount;
    }

    public String getText() {
        return text;
    }

    public static FunctionName fromString(String name) {
        for (var function : values()) {
            if (function.text.equals(name)) {
                return function;
            }
        }
        //надо сделать исключение здесь
        throw new RuntimeException();
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public void checkArgumentCount(int actualCount, int position) {
        if(argumentCount != actualCount) {
            throw new ParseException(String.format("Функция %s ожидает %d аргументов. Передано %d.", text, argumentCount, actualCount), position);
        }
    }
}
