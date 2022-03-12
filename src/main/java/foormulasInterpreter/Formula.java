package foormulasInterpreter;

import foormulasInterpreter.Operations.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class Formula {
    private Node mainNode;
    private final Set<Variable> variables;

    public Formula() {
        variables = new LinkedHashSet<>();
    }

    public void prepare(String formula) throws Exception {
        mainNode = getOperationFromBraces(formula);
    }

    private Operation getOperationFromBraces(String formula) throws Exception {
        formula = getStringWithoutSpaces(formula);
        Operation node = null;
        Node tempNode = null;

        for (int i = 0; i < formula.length(); i++) {

            if (formula.charAt(i) == '(') {
                String braces = getBraces(formula, ++i);
                tempNode = getOperationFromBraces(braces);
                i += braces.length() + 1;

                tryToPinNodeToOperation(node, tempNode);

            } else if (formula.charAt(i) >= '0' && formula.charAt(i) <= '9') {
                tempNode = getNextValue(formula, i);
                i += ((Value) tempNode).getValueLength();

                tryToPinNodeToOperation(node, tempNode);

            } else if (formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z') {
                Variable var = getNextVariable(formula, i);

                if (variables.contains(var)) {
                    tempNode = getVarFromVariables(var);
                } else {
                    tempNode = getNextVariable(formula, i);
                    variables.add((Variable) tempNode);
                }
                i += ((Variable) tempNode).getVarLength();

                tryToPinNodeToOperation(node, tempNode);
            }

            if (i >= formula.length()) {
                return node;
            }

            if (formula.charAt(i) == '*' || formula.charAt(i) == '/') {

                if (formula.charAt(i) == '*') {
                    node = getNewTopNodeWithNewOperation(node, new Multiplication(), tempNode);
                } else {
                    node = getNewTopNodeWithNewOperation(node, new Division(), tempNode);
                }

            } else if (formula.charAt(i) == '+' || formula.charAt(i) == '-') {

                if (formula.charAt(i) == '+') {
                    node = getNewTopNodeWithNewOperation(node, new Addition(), tempNode);
                } else {
                    node = getNewTopNodeWithNewOperation(node, new Subtraction(), tempNode);
                }

                char nextOperationSignOrBraces = getNextOperationOrBrace(formula, i);

                if (nextOperationSignOrBraces == '*' || nextOperationSignOrBraces == '/' || nextOperationSignOrBraces == '(') {
                    Node temp;

                    if (isNextNodeWillVar(formula, i)) {
                        temp = getNextVariable(formula, ++i);
                        variables.add((Variable) temp);
                        i += ((Variable) temp).getVarLength();
                    }
                    else if (nextOperationSignOrBraces == '(') {
                        i++;
                        String braces = getBraces(formula, ++i);
                        temp = getOperationFromBraces(braces);
                        i += braces.length() + 1;

                    } else {
                        temp = getNextValue(formula, ++i);
                        i += ((Value) temp).getValueLength();
                    }

                    if (i >= formula.length()) {
                        node.tryToPinToNode(temp);
                        continue;
                    }

                    Operation operation;
                    if (formula.charAt(i) == '*') {
                        operation = new Multiplication();
                    } else if (formula.charAt(i) == '/') {
                        operation = new Division();
                    } else if (formula.charAt(i) == '+') {
                        operation = new Addition();
                    } else {
                        operation = new Subtraction();
                    }

                    operation.tryToPinToNode(temp);

                    if (isNextNodeWillVar(formula, i)) {
                        Variable var = getNextVariable(formula, ++i);

                        if (variables.contains(var)) {
                            var = getVarFromVariables(var);
                        } else {
                            var = getNextVariable(formula, i);
                            variables.add((Variable) tempNode);
                        }

                        operation.tryToPinToNode(var);
                        i += var.getVarLength() - 1;
                    }
                    else if (formula.charAt(i + 1) == '(') {
                        i++;
                        String braces = getBraces(formula, ++i);
                        Node tempNode1 = getOperationFromBraces(braces);
                        operation.tryToPinToNode(tempNode1);
                        i += braces.length();

                    } else {
                        Value value = getNextValue(formula, ++i);
                        operation.tryToPinToNode(value);
                        i += value.getValueLength() - 1;
                    }

                    node.tryToPinToNode(operation);
                }
            }
        }
        return node;
    }

    public double execute(int ... values) {
        int i = 0;

        for (Variable var: variables) {
            var.setValue(values[i++]);
        }
        return ((Operation) mainNode).executeAndGetValue();
    }

    private Variable getVarFromVariables(Variable variable) {
        for (Variable var : variables) {
            if (var.equals(variable)) {
                return var;
            }
        }
        return null;
    }

    private Operation getNewTopNodeWithNewOperation(Operation mainNode, Operation newNode, Node tempNode) {
        if (mainNode != null) {
            Node temp = mainNode;
            newNode.tryToPinToNode(temp);
            mainNode = newNode;
        } else {
            mainNode = newNode;
            mainNode.tryToPinToNode(tempNode);
        }
        return mainNode;
    }

    private void tryToPinNodeToOperation(Operation nodeToPin, Node nodeWillPinned) throws Exception {
        if (nodeToPin != null) {
            if (!nodeToPin.tryToPinToNode(nodeWillPinned)) {
                throw new Exception("Can't pin node to Operation");
            }
        }
    }

    private boolean isNextNodeWillVar(String formula, int startingPoint) {
        return (formula.charAt(startingPoint + 1) >= 'a' && formula.charAt(startingPoint + 1) <= 'z');
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
    private char getNextOperationOrBrace(String formula, int startingPoint) {
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
                case '(':
                    return '(';
            }
        }

        return '$';
    }

    private Variable getNextVariable(String formula, int startingPoint) {
        StringBuilder temp = new StringBuilder();

        for (; startingPoint < formula.length() &&
                formula.charAt(startingPoint) >= 'a'  && formula.charAt(startingPoint) <= 'z'; startingPoint++) {
            temp.append(formula.charAt(startingPoint));
        }

        return new Variable(temp.toString());
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
