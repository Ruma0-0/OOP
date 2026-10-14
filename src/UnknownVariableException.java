public class UnknownVariableException extends RuntimeException {
    public UnknownVariableException(String name, int position) {
        super("Неизвестное имя переменной" + name + "в позиции" + position);
    }
}
