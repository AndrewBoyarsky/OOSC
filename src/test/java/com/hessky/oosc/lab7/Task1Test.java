package com.hessky.oosc.lab7;


import org.junit.Assert;
import org.junit.Test;

public class Task1Test extends CalculatorTest {

    public Task1Test() {
        super(new Task1());
    }

    @Test
    @Override
    public void collapseSigns() {
        Assert.assertEquals("2+3*(-2-4+(2-5))", calculatorTask.collapseSigns("--2++3*(-2+-4--(2-+5))"));
    }

    @Test
    @Override
    public void findFirstPriorityBraces() throws Exception {
        Assert.assertEquals("1 8", calculatorTask.findFirstPriorityBraces("-(3 - 5 ) * 3 + (-4)*(2/1*0)+(2 * (3+5/(10-4)))", 0).toString());
        Assert.assertEquals("10 13", calculatorTask.findFirstPriorityBraces("--2 * 3 + (-4)*(2/1*0)+(2 * (3+5/(10-4)))", 0).toString());
        Assert.assertEquals("13 19", calculatorTask.findFirstPriorityBraces("--2 * 3 + -4*(2/1*0)+(2 * (3+5/(10-4)))", 0).toString());
        Assert.assertEquals("25 30", calculatorTask.findFirstPriorityBraces("--2 * 3 + -4*0+(2 * (3+5/(10-4)))", 0).toString());
        Assert.assertEquals("20 26", calculatorTask.findFirstPriorityBraces("--2 * 3 + -4*0+(2 * (3+5/6))", 0).toString());
        Assert.assertEquals("15 21", calculatorTask.findFirstPriorityBraces("--2 * 3 + -4*0+(2 * 3)", 0).toString());
        Assert.assertEquals("2 5", calculatorTask.findFirstPriorityBraces("(((-4)))", 0).toString());
        Assert.assertEquals("3 6", calculatorTask.findFirstPriorityBraces("((((-4) + 2)))", 0).toString());
        Assert.assertEquals("0 1", calculatorTask.findFirstPriorityBraces("()()()", 0).toString());
        Assert.assertEquals("0 1", calculatorTask.findFirstPriorityBraces("()", 0).toString());
        Assert.assertEquals("0 1", calculatorTask.findFirstPriorityBraces("()", 0).toString());
    }

    @Test
    @Override
    public void evaluate() throws Exception {
        Assert.assertEquals("12", calculatorTask.evaluate("-(3 - 5 ) * 3 + (-4)*(2/1*0)+(2 * (3+5/(10-4)))").toString());
        Assert.assertEquals("40586624819100", (calculatorTask.evaluate("-3*(2*5*5*(2+2+(2*4-2*(58-65+58*(85-68*45*(548+568-896*852)" +
                ")))))")).toString());
    }

    @Test
    @Override
    public void parse() throws Exception {
        Assert.assertEquals("-193", calculatorTask.parse("-12+3*2+3/1-110*2+23+23/20*1+6").toString());
        Assert.assertEquals("-3", calculatorTask.parse("-3").toString());
        Assert.assertEquals("12345", calculatorTask.parse("12345").toString());
        Assert.assertEquals("0", calculatorTask.parse("2*3/6*0").toString());
        Assert.assertEquals("-2345", calculatorTask.parse("-2345").toString());
        Assert.assertEquals("-34", calculatorTask.parse("-1-3-4-5-6-7-8").toString());
        Assert.assertEquals("-32", calculatorTask.parse("1-3-4-5-6-7-8").toString());
        Assert.assertEquals("364", calculatorTask.parse("12+34+46+56+66+70+80").toString());

    }


}
