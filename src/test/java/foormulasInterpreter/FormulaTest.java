package foormulasInterpreter;


public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("a-bbb*cc");
        double result = formula.execute(10, 5, 2);
        System.out.println(result);
    }
}