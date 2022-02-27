package foormulasInterpreter;

public class Value extends Node{
    private int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
