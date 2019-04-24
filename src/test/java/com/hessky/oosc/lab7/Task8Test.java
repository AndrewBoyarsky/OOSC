package com.hessky.oosc.lab7;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Task8Test extends Task7Test implements CalculatorTest {

    public Task8Test() {
        calculatorTask = new Task8();
    }

    Task8 task8;
    @Before
    public void setUp() {
        task8 = new Task8();
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

    @Test
    public void testEvaluateSimpleExpression() {
        Assert.assertEquals("-1.0", task8.evaluate("1+2*(-1)"));
    }

    @Test
    public void testEvaluateDivision() {
        Assert.assertEquals("15.5", task8.evaluate("2/4 + (10*1.5)"));
    }

    @Test
    public void testMultiplyingWithBraces() {
        Assert.assertEquals("7.0", task8.evaluate("2+(-5)*(7-8)"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluateIncorrectBraces() {
        task8.evaluate("-(1 + 5* (-2 + (-3)");
    }

    @Test
    public void testEvaluateMinusMinus() {
        Assert.assertEquals("4.0", task8.evaluate("--4"));
    }

    @Test
    public void evaluateComplexMinus() {
        Assert.assertEquals("-6.0", task8.evaluate("-(4*2-1) +  -(3-4)"));
    }

    @Test
    public void testTan() {
        Assert.assertEquals("0.5463024898437905", task8.evaluate("tan(0.5)"));
    }

    @Test
    public void testMatrix() {
        Assert.assertEquals("[-3.0 -7.0 -11.0 ; ]", task8.evaluate("-[1 3 6]+-[2 4 5]"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddIncorrectMatrices() {
        task8.evaluate("[3 4] - [3]");
    }

    @Test
    public void testMinusMatrix() {
        Assert.assertEquals("[-3.0 -6.0 6.0 -41.0 ; ]", task8.evaluate("-[3 6 -6 41]"));
    }
}
