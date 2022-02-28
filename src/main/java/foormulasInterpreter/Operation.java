package foormulasInterpreter;

public class Operation extends Node {
    private char operationSign;
    private double leftValue;
    private double rightValue;

    public Operation(Operation operation) {
        setOperationSign(operation.getOperationSign());
        setRightNode(operation.getRightNode());
        setLeftNode(operation.getLeftNode());
    }

    public Operation() {
    }

    public char getOperationSign() {
        return operationSign;
    }

    protected void setValues() {
        if (getLeftNode() instanceof  Value) {
            leftValue = ((Value) getLeftNode()).getValue();
        } else {
            Operation operation = (Operation) getLeftNode();
            leftValue = operation.executeAndGetValue();
        }

        if (getRightNode() instanceof Value) {
            rightValue = ((Value) getRightNode()).getValue();
        } else {
            Operation operation = (Operation) getRightNode();
            rightValue = operation.executeAndGetValue();
        }
    }

    protected boolean tryToPinToNode(Node nodeToPin) {
        if (getLeftNode() == null) {
            setLeftNode(nodeToPin);
        } else if (getRightNode() == null) {
            setRightNode(nodeToPin);
        } else {
            return false;
        }

        return true;
    }


    public double executeAndGetValue() {
        return Double.MIN_VALUE;
    }

    public void setOperationSign(char operationSign) {
        this.operationSign = operationSign;
    }


    public void setLeftValue(double leftValue) {
        this.leftValue = leftValue;
    }

    public void setRightValue(double rightValue) {
        this.rightValue = rightValue;
    }
    public double getLeftValue() {
        return leftValue;
    }

    public double getRightValue() {
        return rightValue;
    }
}
