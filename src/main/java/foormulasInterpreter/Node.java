package foormulasInterpreter;

public abstract class Node {
    private Node rightNode;
    private Node leftNode;


    public Node getRightNode() {
        return rightNode;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }
}
