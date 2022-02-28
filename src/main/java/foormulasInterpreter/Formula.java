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
        Node node = new Operation();
        Node nodeFromBraces;
        int amountOfOpenedBraces = 0;


        for (Integer i = 0; i < formula.length(); i++) {
            char currentChar = formula.charAt(i);

            if (currentChar == '(') {
                nodeFromBraces = getOperationFromBraces(getBraces(formula, ++i));
                amountOfOpenedBraces++;
            }

            if (currentChar <= 9) {

            }


        }


        return (Operation) node;
    }

    private int getValue(String formula, Integer startingPoint) {

    }

    private String getBraces(String formula, Integer startingPoint) {
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
