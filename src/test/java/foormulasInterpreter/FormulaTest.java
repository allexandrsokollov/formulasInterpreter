package foormulasInterpreter;


import org.junit.Assert;

public class FormulaTest {

    @org.junit.Test
    public void prepare() throws Exception {
        Formula formula = new Formula();
        formula.prepare("4*x*8-(2+2*2) * (2*y*2+2)");
        double result = formula.execute(5, 2);
        Assert.assertEquals(100, result, 0.00002);

        Formula formula2 = new Formula();
        formula2.prepare("(10*5)-(x*x+y*y)*(25*5)+(2+2*2)");
        double result2 = formula2.execute(5, 2);
        Assert.assertEquals(-3569, result2, 0.00002);

        Formula formula3 = new Formula();
        formula3.prepare("(2+2)+(x*x)*(y*y)*(x/x)");
        double result3 = formula3.execute(5, 2);
        Assert.assertEquals(104, result3, 0.00002);

        Formula formula4 = new Formula();
        formula4.prepare("(x/x)*(x/x)*(2+2)+(x*x)*(y*y)*(x/x)");
        double result4 = formula4.execute(5, 2);
        Assert.assertEquals(104, result4, 0.00002);

        Formula formula5 = new Formula();
        formula5.prepare("(10*5)-(x*x+y*y)*(25*5)+(2+2*2)+1000*(x/x)*(x/x)");
        double result5 = formula5.execute(5, 2);
        Assert.assertEquals(-2569, result5, 0.00002);
    }
}