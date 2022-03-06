package foormulasInterpreter;


public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("a-bbb*cc");
        double result = formula.getResult();
        System.out.println(result);
    }
}