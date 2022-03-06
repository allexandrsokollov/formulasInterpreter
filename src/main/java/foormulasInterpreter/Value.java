package foormulasInterpreter;

public class Value extends Node{
    private int value;
    private final int valueLength;

    public Value(int value) {
        this.value = value;
        valueLength = String.valueOf(value).length();
    }

    public int getValueLength() {
        return valueLength;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
