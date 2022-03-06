package foormulasInterpreter.Operations;

import foormulasInterpreter.Value;

public class Subtraction extends Operation {
    public Subtraction() {
        setOperationSign('-');
    }

    @Override
    public double executeAndGetValue() {
        double left, right;

        if (getLeftNode() instanceof Value) {
            left = ((Value) getLeftNode()).getValue();
        } else {
            left = ((Operation) getLeftNode()).executeAndGetValue();
        }

        if (getRightNode() instanceof Value) {
            right = ((Value) getRightNode()).getValue();
        } else {
            right = ((Operation) getRightNode()).executeAndGetValue();
        }

        return left - right;
    }
}
