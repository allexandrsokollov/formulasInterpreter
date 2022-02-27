package foormulasInterpreter;

public class Formula {
    private Node mainNode;

    public Formula() {
    }

    public void prepare(String formula) {
        mainNode = getOperationFromBraces(new StringBuilder(formula));

    }

    private Operation getOperationFromBraces(StringBuilder formula) {
        StringBuilder tempBraces = new StringBuilder();
        Value tempLeftVal;
        Value tempRightVal;
        Operation temp = null;
        int amountOfOpenedBraces = 0;
        boolean bracesInAction = false;

        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(' && amountOfOpenedBraces == 0) {
                amountOfOpenedBraces++;
                bracesInAction = true;
                continue;
            }

            if (bracesInAction) {
                tempBraces.append(formula.charAt(i));

                if (formula.charAt(i) == '(') {
                    amountOfOpenedBraces++;
                }

                if (formula.charAt(i) == ')') {
                    amountOfOpenedBraces--;
                }

                if (amountOfOpenedBraces == 0) {
                    if (temp == null) {
                        temp = getOperationFromBraces(new StringBuilder(tempBraces));
                    } else {
                        temp.setLeftNode(getOperationFromBraces(tempBraces));
                    }
                    tempBraces.replace(0, tempBraces.length(), "");
                }
                continue;
            }
        }


    }
}
