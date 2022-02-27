package foormulasInterpreter;

public class Variable {
    private final String varName;
    private int value;

    public Variable(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
