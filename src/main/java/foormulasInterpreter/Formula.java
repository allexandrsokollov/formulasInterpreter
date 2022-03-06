package foormulasInterpreter;

import foormulasInterpreter.Operations.*;

import java.util.HashSet;
import java.util.Set;

public class Formula {
    private Node mainNode;
    private Set<Variable> variables;

    public Formula() {
        variables = new HashSet<>();
    }

    public void prepare(String formula) {
        mainNode = getOperationFromBraces(formula);
    }

    private Operation getOperationFromBraces(String formula) {
        formula = getStringWithoutSpaces(formula);
        Operation node = null;
        Node tempNode = null;

        for (int i = 0; i < formula.length(); i++) {

            if (formula.charAt(i) == '(') {
                String braces = getBraces(formula, ++i);
                tempNode = getOperationFromBraces(braces);
                i += braces.length() + 1;

                if (node != null) {
                    if (!node.tryToPinToNode(tempNode)) {
                        System.out.println("can't pin");
                    }
                }

            } else if (formula.charAt(i) >= '0' && formula.charAt(i) <= '9') {
                tempNode = getNextValue(formula, i);
                i += ((Value) tempNode).valueLength;

                if (node != null) {
                    if (!node.tryToPinToNode(tempNode)) {
                        System.out.println("can't pin");
                    }
                }

            } else if (formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z') {

            }

            if (i < formula.length() && formula.charAt(i) == '*' || formula.charAt(i) == '/') {
                Operation operation;

                if (formula.charAt(i) == '*') {
                    operation = new Multiplication();
                } else {
                    operation = new Division();
                }

                if (node != null) {
                    Node tmp = node;
                    operation.tryToPinToNode(tmp);
                    node = operation;

                } else {
                    node = operation;
                    node.tryToPinToNode(tempNode);
                }

            } else if (formula.charAt(i) == '+' || formula.charAt(i) == '-') {
                Operation tempOperation;

                if (formula.charAt(i) == '+') {
                    tempOperation = new Addition();
                } else {
                    tempOperation = new Subtraction();
                }

                if (node != null) {
                    Node temp = node;
                    tempOperation.tryToPinToNode(temp);
                    node = tempOperation;
                } else {
                    node = tempOperation;
                    node.tryToPinToNode(tempNode);
                }

                char nextOperationSign = getNextOperation(formula, i);

                if (nextOperationSign == '*' || nextOperationSign == '/') {

                    Value temp = getNextValue(formula, ++i);
                    i += temp.valueLength;

                    Operation operation;
                    if (nextOperationSign == '*') {
                        operation = new Multiplication();
                    } else {
                        operation = new Division();
                    }

                    operation.tryToPinToNode(temp);
                    operation.tryToPinToNode(getNextValue(formula, ++i));

                    i += temp.valueLength;
                    node.tryToPinToNode(operation);

                }
            }
        }
        return node;
    }

    public double getResult() {
        return ((Operation) mainNode).executeAndGetValue();
    }

    private String getStringWithoutSpaces(String string) {
        StringBuilder sb = new StringBuilder();

        for (char ch : string.toCharArray()) {
            if (ch != ' ') {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * @return '$' if nothing found
     * else returns next operation
     */
    private char getNextOperation(String formula, int startingPoint) {
        startingPoint++;

        for (; startingPoint < formula.length(); startingPoint++) {
            switch (formula.charAt(startingPoint)) {
                case '*':
                    return '*';
                case '+':
                    return '+';
                case '-':
                    return '-';
                case '/':
                    return '/';
            }
        }

        return '$';
    }

    private String getVariable(String formula, int startingPoint) {
        StringBuilder temp = new StringBuilder();

        for (; startingPoint < formula.length() &&
                formula.charAt(startingPoint) >= 'a'  && formula.charAt(startingPoint) <= 'z'; startingPoint++) {
            temp.append(formula.charAt(startingPoint));
        }

        return temp.toString();
    }

    private Value getNextValue(String formula, int startingPoint) {
        StringBuilder temp = new StringBuilder();

        for (; startingPoint < formula.length() &&
                formula.charAt(startingPoint) >= '0'  && formula.charAt(startingPoint) <= '9'; startingPoint++) {
            temp.append(formula.charAt(startingPoint));
        }

        return new Value(Integer.parseInt(temp.toString()));
    }

    private String getBraces(String formula, int startingPoint) {
        StringBuilder result = new StringBuilder();
        int amountOfBraces = 1;

        for (; startingPoint < formula.length(); startingPoint++) {
            char temp = formula.charAt(startingPoint);

            if (temp == '(') {
                amountOfBraces++;
            }

            if (temp == ')') {
                amountOfBraces--;

                if (amountOfBraces == 0) {
                    break;
                }
            }
            result.append(formula.charAt(startingPoint));
        }
        return result.toString();
    }
}
