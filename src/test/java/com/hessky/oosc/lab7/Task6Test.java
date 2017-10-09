package com.hessky.oosc.lab7;

import org.junit.Assert;

public class Task6Test extends CalculatorTest {

    public Task6Test() {
        super(new Task6());
    }

    @Override
    public void evaluate() throws Exception {
        Assert.assertTrue(Long.parseLong(calculatorTask.evaluate("(0 \\/ ((1))) /\\ 1")) == 0);
        Assert.assertTrue(Long.parseLong(calculatorTask.evaluate("(0 \\/ !(!(!1))) /\\ 1")) == 0);
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
