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
        mainNode = getOperationFromBraces(new StringBuilder(formula));
    }

    private Operation getOperationFromBraces(StringBuilder formula) {
        StringBuilder tempBraces = new StringBuilder();
        StringBuilder tempVarName = new StringBuilder();
        StringBuilder tempValue = new StringBuilder();
        Node node = null;
        int amountOfOpenedBraces = 0;
        boolean bracesInAction = false;
        boolean varNameReading = false;
        boolean valueReading = false;

        for (int i = 0; i < formula.length(); i++) {
            char currentChar = formula.charAt(i);

            if (currentChar == '(' && amountOfOpenedBraces == 0) {
                amountOfOpenedBraces++;
                bracesInAction = true;
                continue;
            }

            if (bracesInAction) {
                tempBraces.append(currentChar);

                if (currentChar == '(') {
                    amountOfOpenedBraces++;
                }

                if (currentChar == ')') {
                    amountOfOpenedBraces--;
                }

                if (amountOfOpenedBraces == 0) {
                    if (node == null) {
                        node = getOperationFromBraces(new StringBuilder(tempBraces));
                    } else if (node instanceof Operation) {
                        Operation newTemp = getOperationFromBraces(tempBraces);
                        newTemp.setRightNode(new Operation((Operation) node));
                        node = newTemp;
                    }else {
                        Operation newTemp = getOperationFromBraces(tempBraces);
                        newTemp.setLeftNode(new Operation((Operation) node));
                        node = newTemp;
                    }
                    tempBraces.replace(0, tempBraces.length(), "");
                    bracesInAction = false;
                }
                continue;
            }


            if (currentChar >= 'a' && currentChar <= 'z') {
                tempVarName.append(currentChar);
                varNameReading = true;
            } else if (varNameReading) {
                variables.add(new Variable(tempVarName.toString()));
                varNameReading = false;

                if (node == null) {
                    node = new Variable(tempVarName.toString());
                } else if (node instanceof  Operation) {
                    if (node.getRightNode() == null) {
                        node.setRightNode(new Variable(tempVarName.toString()));
                    }
                }

                tempVarName.replace(0, tempVarName.length(), "");
            }

            if (currentChar <= 9) {
                tempValue.append(currentChar);
                valueReading = true;
            } else if (valueReading) {
                valueReading = false;

                if (node == null) {
                    node = new Value((int) Double.parseDouble(tempValue.toString()));
                } else if (node instanceof Operation) {
                    node.setRightNode(new Value((int) Double.parseDouble(tempValue.toString())));
                }

                tempValue.replace(0, tempValue.length(), "");
            }


            if (currentChar == '*') {
                Multiplication multiplication = new Multiplication();
                Node temp = node;
                multiplication.setLeftNode(temp);
                node = multiplication;
                continue;
            }
        }
        return (Operation) node;
    }
}
