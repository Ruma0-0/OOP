package model;

import exceptions.ExpressionException;
import exceptions.ParseException;

/**
 * Встроенные функции с фиксированным количеством аргументов.
 */
public enum FunctionName {
    /** Минимум из двух чисел.*/
    MIN("min", 2),
    /** Максимум из двух чисел.*/
    MAX("max", 2),
    /** Модуль числа.*/
    ABS("abs", 1);

    private final String name;
    private final int argumentCount;

    FunctionName(String name, int argumentCount) {
        this.name = name;
        this.argumentCount = argumentCount;
    }

    /**
     * @return текстовое имя функции (как в выражении)
     */
    public String getName() {
        return name;
    }

    /**
     * Находит функцию по её текстовому имени.
     *
     * @param name имя функции
     * @param position позиция функции в строке
     * @return соответствующая функция
     * @throws ExpressionException если функция с таким именем не найдена
     */
    public static FunctionName fromString(String name, int position) {
        for (var function : values()) {
            if (function.name.equals(name)) {
                return function;
            }
        }

        throw new ParseException("Неизвестная функция: " + name, position);
    }
    /**
     * @return ожидаемое количество аргументов
     */
    public int getArgumentCount() {
        return argumentCount;
    }

    /**
     * Проверяет, что передано ожидаемое количество аргументов.
     *
     * @param actualCount фактическое количество аргументов
     * @param position  позиция функции в строке
     * @throws ParseException если количество не совпадает
     */
    public void checkArgumentCount(int actualCount, int position) {
        if(argumentCount != actualCount) {
            throw new ParseException(String.format("Функция %s ожидает %d аргументов. Передано %d.", name, argumentCount, actualCount), position);
        }
    }
}
