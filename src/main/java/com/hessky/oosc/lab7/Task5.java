package com.hessky.oosc.lab7;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 extends Task4 implements CalculatorTask {

    @Override
    public String evaluate(String expression) {
        Objects.requireNonNull(expression, "String must be not null");
        if (expression.isEmpty()) return "";
        try {

            if (expression.contains(":")) {
                String args = expression.substring(expression.indexOf(":") + 1);

                Pattern pattern = Pattern.compile("[A-Za-z]+=\\d+\\.?\\d*");
                Matcher matcher = pattern.matcher(args);
                while (matcher.find()) {
                    String par = matcher.group();
                    String[] parameterAndValue = par.split("=");
                    String parameter = parameterAndValue[0];
                    String value = parameterAndValue[1];
                    expression = expression.replaceAll(parameter, value);
                }
                return super.evaluate(expression.substring(0, expression.indexOf(":")));
            } else return super.evaluate(expression);
        }
        catch (Throwable e) {
            throw new IllegalArgumentException("Expression contains errors! " + expression, e);
        }
    }
}
