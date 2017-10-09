package com.hessky.oosc.lab7;

import org.junit.Assert;
import org.junit.Test;

public class Task2Test extends CalculatorTest {

    public Task2Test() {
        super(new Task2());
    }

    @Test
    @Override
    public void evaluate() throws Exception {
        Assert.assertEquals("0.0", calculatorTask.evaluate("(1 + 2) / 6 - 1 + 0.5"));
    }

    @Test
    @Override
    public void collapseSigns() throws Exception {
    }

    @Override
    @Test
    public void findFirstPriorityBraces() throws Exception {

    }

    @Test
    @Override
    public void parse() throws Exception {
        Assert.assertEquals("12.5", calculatorTask.parse("12.1+0.4"));
        Assert.assertEquals("12.0", calculatorTask.parse("12.2+0.4*-0.5-0.33+0.66*0.5"));
//        Assert.assertEquals("12.5", calculatorTask.parse("12.1+0.4").toString());
    }


}
