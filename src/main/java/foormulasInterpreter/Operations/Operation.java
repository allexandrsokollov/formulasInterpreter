package foormulasInterpreter.Operations;

import foormulasInterpreter.Node;

public class Operation extends Node {
    private char operationSign;

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


        public boolean tryToPinToNode(Node nodeToPin) {
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


}
