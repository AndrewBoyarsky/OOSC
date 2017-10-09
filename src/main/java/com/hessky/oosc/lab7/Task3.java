package com.hessky.oosc.lab7;

/**
 * 3. Додати можливість обчислення арифметичних виразів, що включають дійсні числа та
 * константи e та pi (число Пі), наприклад,
 * (1.0 + 2.5)*pi + e
 */
public class Task3 extends Task2 implements CalculatorTask {

    @Override
    public String evaluate(String expression) {
        String temp = expression;
        temp = temp.replaceAll("[eE]", String.valueOf(Math.E));
        temp = temp.replaceAll("pi|Pi|PI|pI", String.valueOf(Math.PI));
        return super.evaluate(temp);
    }
}
