package com.hessky.oosc.lab7;
/**

 */

import org.junit.Assert;

public class Task3Test extends CalculatorTest{
    public Task3Test() {
        super(new Task3());
    }
    @Override
    public void evaluate() throws Exception {
        Assert.assertEquals("33.24972512560283", calculatorTask.evaluate("(-pi* e)/234*10-(1+pi)*(e*e/(pi-3.5)+12.5)"));
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
