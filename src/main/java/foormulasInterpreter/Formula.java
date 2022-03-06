package foormulasInterpreter;

import java.util.HashSet;
import java.util.Set;
/*

got to: 
* write next operation checker DONE
* Implement addition
* Implement division
* Implement subtraction
* write Executor Interface, implement node execution
* check out 2+2*2, 2*2+2+2*2 etc. DONE. WORKS CORRECTLY

*/

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

            if (i < formula.length() && formula.charAt(i) == '*') {
                Multiplication multiplication = new Multiplication();

                if (node != null) {
                    Node tmp = node;
                    multiplication.tryToPinToNode(tmp);
                    node = multiplication;

                } else {
                    node = multiplication;
                    node.tryToPinToNode(tempNode);
                }

            } else if (i < formula.length() && formula.charAt(i) == '+') {
                Addition addition = new Addition();

                if (node != null) {
                    Node temp = node;
                    addition.tryToPinToNode(temp);
                    node = addition;
                } else {
                    node = addition;
                    node.tryToPinToNode(tempNode);
                }

                if (getNextOperation(formula, i) == '*') {
                    Node temp = getNextValue(formula, ++i);
                    i += ((Value) temp).valueLength;

                    Multiplication multiplication = new Multiplication();
                    multiplication.tryToPinToNode(temp);

                    multiplication.tryToPinToNode(getNextValue(formula, ++i));
                    i += ((Value) temp).valueLength;
                    node.tryToPinToNode(multiplication);

                }




            }

        }
        return node;
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
