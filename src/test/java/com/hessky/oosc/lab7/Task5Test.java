package com.hessky.oosc.lab7;

import org.junit.Assert;

public class Task5Test extends Task4Test implements CalculatorTest {
    public Task5Test() {
        calculatorTask = new Task5();
    }

    @Override
    public void evaluate() throws Exception {
        super.evaluate();
        Assert.assertTrue(Math.abs(Double.parseDouble(calculatorTask.evaluate("(1.5 + sin(a + cos(b)))*3:a=2 b=3.5")) - 7.1222) < 0.0001);
    }
}
