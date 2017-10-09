package com.hessky.oosc.lab7;

import org.junit.Test;

public abstract class CalculatorTest {
    protected CalculatorTask calculatorTask;

    public CalculatorTest(CalculatorTask calculatorTask) {
        this.calculatorTask = calculatorTask;
    }

    @Test
    public abstract void evaluate() throws Exception;

    @Test
    public abstract void parse() throws Exception;

    @Test
    public abstract void collapseSigns() throws Exception;
    @Test
    public abstract void findFirstPriorityBraces() throws Exception;
}
