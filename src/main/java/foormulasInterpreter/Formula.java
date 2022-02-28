package foormulasInterpreter;

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

            } else if (formula.charAt(i) >= 48 && formula.charAt(i) <= 57) {
                String tempValue = getValue(formula, i);
                i += tempValue.length();
                tempNode = new Value(Integer.parseInt(tempValue));

                if (node != null) {
                    if (!node.tryToPinToNode(tempNode)) {
                        System.out.println("can't pin");
                    }
                }
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

            }

        }
        return node;
    }

    private String getValue(String formula, int startingPoint) {
        StringBuilder temp = new StringBuilder();

        for (; startingPoint < formula.length() &&
                formula.charAt(startingPoint) <= 57 && formula.charAt(startingPoint) >= 48 ; startingPoint++) {
            temp.append(formula.charAt(startingPoint));
        }

        return temp.toString();
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
