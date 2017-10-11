package com.hessky.oosc.lab7;

import java.util.Objects;

/**
 * 3. Додати можливість обчислення арифметичних виразів, що включають дійсні числа та
 * константи e та pi (число Пі), наприклад,
 * (1.0 + 2.5)*pi + e
 */
public class Task3 extends Task2 implements CalculatorTask {

    @Override
    public String evaluate(String expression) {
        Objects.requireNonNull(expression, "String must be not null");
        if (expression.isEmpty()) return "";
        try {
            String temp = expression;
            temp = temp.replaceAll("[eE]", String.valueOf(Math.E));
            temp = temp.replaceAll("pi|Pi|PI|pI", String.valueOf(Math.PI));
            return super.evaluate(temp);
        }
        catch (Throwable e) {
            throw new IllegalArgumentException("Expression contains errors! " + expression, e);
        }
    }
}
