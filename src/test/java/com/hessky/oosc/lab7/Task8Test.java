package com.hessky.oosc.lab7;

import org.junit.Assert;
import org.junit.Test;

public class Task8Test extends Task7Test implements CalculatorTest {

    public Task8Test() {
        calculatorTask = new Task8();
    }

    @Override
    public void evaluate() throws Exception {
        super.evaluate();
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)*x^2+x^2/(-x^3+4-x^2)*x^1/3.5-2x^3+(12x^2+x^4)/(x^2-x^3-4)*(x^2+3x^5)");
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)*x^2+x^2/(-x^3+4-x^2)*x^1/3.5*(2x^3+(12x^2+x^4)/(x^2-x^3-4)*(x^2+3x^5))");
        calculatorTask.evaluate("((-4x^2 - 3x^4 + 3)*x^2)*x^2/(-x^3+4-x^2)*x^1/3.5*(2x^3+(12x^2+x^4)/(x^2-x^3-4)*(x^2+3x^5))");
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)/(x^2+1)");
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)*(x^2+1)");
    }

    @Test
    public void testFindExpression() throws Exception {
        Task8 task8 = new Task8();
        Assert.assertEquals(new Coordinates(0, 90), task8.findExpression("((-4x^2 - 3x^4 + 3)*x^2)*x^2/(-x^3+4-x^2)*x^1/3.5*(2x^3+(12x^2+x^4)/" +
                        "(x^2-x^3-4)*(x^2+3x^5))"));
        Assert.assertEquals(new Coordinates(4, 28),
                task8.findExpression("x^2+((-4x^2 - 3x^4 + 3)*x^2)+((x^2-x^3-4)*(x^2+3x^5))"));
        Assert.assertEquals(new Coordinates(4, 52),
                task8.findExpression("x^2+((-4x^2 - 3x^4 + 3)*x^2)*((x^2-x^3-4)*(x^2+3x^5))"));
        Assert.assertEquals(new Coordinates(4, 40),
                task8.findExpression("x^2+((-4x^2 - 3x^4 + 3)*x^2)*(x^2-x^3-4)+(x^2+3x^5)"));
        Assert.assertEquals(new Coordinates(0, 52),
                task8.findExpression("(x^2+((-4x^2 - 3x^4 + 3)*x^2)*(x^2-x^3-4)+(x^2+3x^5))"));
        Assert.assertEquals(new Coordinates(4, 22),
                task8.findExpression("x^2+x^3*(3x^6-0.9x^30)+x^100"));
    }
}
