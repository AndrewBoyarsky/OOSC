package com.hessky.oosc.lab7;

import org.junit.Assert;

public class Task4Test extends CalculatorTest {
    public Task4Test() {
        super(new Task4());
    }

    @Override
    public void evaluate() throws Exception {
        Assert.assertTrue(Math.abs(Double.parseDouble(calculatorTask.evaluate("(1.5 + sin(2.0 + cos(3.5)))*3")) - 7.1222) < 0.0001);
    }

    @Override
    public void parse() throws Exception {

    }

    @Override
    public void collapseSigns() throws Exception {

    }

    @Override
    public void findFirstPriorityBraces() throws Exception {

    }
}
