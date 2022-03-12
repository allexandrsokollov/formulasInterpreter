package foormulasInterpreter;


public class Value extends Node{
    private int value;
    private int valueLength;

    public Value(int value) {
       setValue(value);
    }

    public int getValueLength() {
        return valueLength;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.valueLength = String.valueOf(value).length();
    }

    @Override
    public String toString() {
        return "value: " + getValue();
    }


}
