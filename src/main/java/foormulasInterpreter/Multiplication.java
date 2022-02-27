package foormulasInterpreter;

public class Multiplication extends Operation{
    public Multiplication() {
        setOperationSign('*');
    }

    @Override
    public double executeAndGetValue() {
        setValues();
        return getLeftValue() * getRightValue();
    }
}
