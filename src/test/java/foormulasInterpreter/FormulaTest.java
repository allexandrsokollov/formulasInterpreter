package foormulasInterpreter;


public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("1*2+3+4*5");
    }
}