package foormulasInterpreter;

public class Value extends Node{
    private int value;
    public final int valueLength;

    public Value(int value) {
        this.value = value;
        valueLength = String.valueOf(value).length();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
