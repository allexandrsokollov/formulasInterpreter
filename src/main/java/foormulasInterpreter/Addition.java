package foormulasInterpreter;

public class Addition extends Operation{

    public Addition() {
        setOperationSign('+');
    }

    @Override
    public double executeAndGetValue() {
        setValues();
        return getLeftValue() + getRightValue();
    }
}
