package foormulasInterpreter;


public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("5*(3*2)*6");
    }
}