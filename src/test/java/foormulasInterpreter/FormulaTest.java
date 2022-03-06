package foormulasInterpreter;


public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("4-2*2");
        double result = formula.getResult();
        System.out.println(result);
    }
}