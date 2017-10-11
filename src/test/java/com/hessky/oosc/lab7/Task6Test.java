package com.hessky.oosc.lab7;

import org.junit.Assert;

public class Task6Test extends Task5Test implements CalculatorTest {

    public Task6Test() {
        calculatorTask = new Task6();
    }

    @Override
    public void evaluate() throws Exception {
        super.evaluate();
        Assert.assertEquals("1", calculatorTask.evaluate("(0 \\/ ((1))) /\\ 1"));
        Assert.assertEquals("0",calculatorTask.evaluate("(0 \\/ !(!(!1))) /\\ 1"));
    }
}
