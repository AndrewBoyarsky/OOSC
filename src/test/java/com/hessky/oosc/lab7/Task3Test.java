package com.hessky.oosc.lab7;
/**

 */

import org.junit.Assert;

public class Task3Test extends Task2Test implements CalculatorTest{
    public Task3Test() {
        calculatorTask = new Task3();
    }
    @Override
    public void evaluate() throws Exception {
        super.evaluate();
        Assert.assertEquals("33.24972512560283", calculatorTask.evaluate("(-pi* e)/234*10-(1+pi)*(e*e/(pi-3.5)+12.5)"));
    }
}
