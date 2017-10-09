package com.hessky.oosc.lab7;

import org.junit.Assert;

public class Task7Test extends CalculatorTest {
    public Task7Test() {
        super(new Task7());
    }

    @Override
    public void evaluate() throws Exception {
        Assert.assertEquals("[2.0 0.0 ; 0.0 0.0 ; ]", calculatorTask.evaluate("-[2 3;43 -12] + [4 3;43 -12]"));
        Assert.assertEquals("[10.5 -30.0 ; 187.5 273.0 ; ]", calculatorTask.evaluate("-[2 3;43 -12] * -[4.5 3;0.5 -12]"));
        Assert.assertEquals("[-6.5 -6.0 ; -43.5 24.0 ; ]", calculatorTask.evaluate("-[2 3;43 -12] -  [4.5 3;0.5 -12]"));
        Assert.assertEquals("[-234.4 57.599999999999994 ; -570.7 166.49999999999997 ; -2325.015 449.346 ; -2281.3740000000003 834.4037000000001 ; ]", calculatorTask.evaluate("[1 5.3 ; 1.3 13.2; -41 56; -34.123 67.34 ]*-[2 3;43 -12] -  " +
                "[4.5 3;0.5 -12; -0.985 345.654; -546 76.0453]"));
        Assert.assertEquals("[-234.33252063251095 57.49892250317158 ; -570.0217937162085 166.1996139007956 ; -2302.2065299965257 446.1710494675259 ; -2260.4510637484523 831.2060485788703 ; ]", calculatorTask.evaluate("[1 5.3 ; 1.3 13.2; -41 56; -34.123 67.34 ]*-([2 3;43 -12]+inv([3.5 5.34; 12.23 56])) -  " +
                "[4.5 3;0.5 -12; -0.985 345.654; -546 76.0453]"));

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
