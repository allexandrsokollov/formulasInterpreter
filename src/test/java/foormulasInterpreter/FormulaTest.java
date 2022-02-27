package foormulasInterpreter;


public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("x*(x*y)*z");
    }
}