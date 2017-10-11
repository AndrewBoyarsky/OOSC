package com.hessky.oosc.lab7;

import org.junit.Assert;

public class Task4Test extends Task3Test implements CalculatorTest {
    public Task4Test() {
        calculatorTask = new Task4();
    }

    @Override
    public void evaluate() throws Exception {
        super.evaluate();
        Assert.assertTrue(Math.abs(Double.parseDouble(calculatorTask.evaluate("(1.5 + sin(2.0 + cos(3.5)))*3")) - 7.1222) < 0.0001);
    }
}
