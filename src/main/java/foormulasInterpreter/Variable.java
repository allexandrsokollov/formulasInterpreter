package foormulasInterpreter;

public class Variable extends Value{
    private final String varName;
    private int value;

    public Variable(String varName) {
        super(Integer.MIN_VALUE);
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }

    public int getVarLength() {
        return varName.length();
    }


}
