package com.hessky.oosc.lab7;

public class Task8Test extends CalculatorTest {

    public Task8Test() {
        super(new Task8());
    }

    @Override
    public void evaluate() throws Exception {
//        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)*x^2+x^2/(-x^3+4-x^2)*x^1/3.5-2x^3+(12x^2+x^4)/(x^2-x^3-4)*(x^2+3x^5)");
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)*x^2+x^2/(-x^3+4-x^2)*x^1/3.5*(2x^3+(12x^2+x^4)/(x^2-x^3-4)*(x^2+3x^5))");
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)/(x^2+1)");
        calculatorTask.evaluate("(-4x^2 - 3x^4 + 3)*(x^2+1)");
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
