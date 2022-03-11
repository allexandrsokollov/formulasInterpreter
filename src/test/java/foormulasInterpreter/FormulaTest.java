package foormulasInterpreter;


import org.junit.Assert;

public class FormulaTest {

    @org.junit.Test
    public void prepare() {
//        Formula formula = new Formula();
//        formula.prepare("4*x*8-(2+2*2) * (2*y*2+2)");
//        double result = formula.execute(5, 2);
//        Assert.assertEquals(100, result, 0.00002);

        Formula formula2 = new Formula();
        formula2.prepare("(12 + 10*5)-(x+x*x+y*y+y)*(25+25+25*5)+(2*2+2+2*2)");
        double result2 = formula2.execute(5, 2);
        Assert.assertEquals(2522, result2, 0.00002);
    }
}