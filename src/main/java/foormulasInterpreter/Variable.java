package foormulasInterpreter;

import java.util.Objects;

public class Variable extends Value{
    private final String varName;

    public Variable(String varName) {
        super(Integer.MIN_VALUE);
        this.varName = varName;
    }

    public int getVarLength() {
        return varName.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(varName, variable.varName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varName);
    }
}
