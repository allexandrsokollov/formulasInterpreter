package foormulasInterpreter;


import org.junit.Assert;

public class FormulaTest {

    @org.junit.Test
    public void prepare() {
        Formula formula = new Formula();
        formula.prepare("4*x*8-(2+2*2) * (2*y*2+2)");
        double result = formula.execute(5, 2);
        Assert.assertEquals(100, result, 0.00002);
    }
}