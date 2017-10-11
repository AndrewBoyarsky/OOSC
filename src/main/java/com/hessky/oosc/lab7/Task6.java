package com.hessky.oosc.lab7;

import java.util.Objects;

public class Task6 extends Task5 implements CalculatorTask {
    private boolean isBool;

    private void checkBool(String expression) {
        isBool = expression.contains("\\/") || expression.contains("!") || expression.contains("/\\");
    }

    @Override
    public String evaluate(String expression) {
        Objects.requireNonNull(expression, "String must be not null");
        if (expression.isEmpty()) return "";
        checkBool(expression);
        return super.evaluate(expression);
    }

    @Override
    public String parse(String expression) {
        if (isBool) {
            expression=removeDecimalPart(expression);
            while (expression.contains("!")) {
                int inversionIndex = expression.indexOf("!");
                byte booleanValue = (byte) (expression.charAt(inversionIndex + 1) == '0' ? 0 : 1);
                expression = expression.replaceFirst("!" + booleanValue, String.valueOf(booleanValue^1));
            }
            while (expression.contains("/\\")) {
                int conjunctionIndex = expression.indexOf("/\\");
                byte firstBooleanValue = (byte) (expression.charAt(conjunctionIndex -1) == '0' ? 0 : 1);
                byte secondBooleanValue = (byte) (expression.charAt(conjunctionIndex +2) == '0' ? 0 : 1);
                expression = expression.replaceFirst(firstBooleanValue + "/\\\\" + secondBooleanValue, String.valueOf(firstBooleanValue &
                        secondBooleanValue));
            }
            while (expression.contains("\\/")) {
                int disjunctionIndex = expression.indexOf("\\/");
                byte firstBooleanValue = (byte) (expression.charAt(disjunctionIndex -1) == '0' ? 0 : 1);
                byte secondBooleanValue = (byte) (expression.charAt(disjunctionIndex + 2) == '0' ? 0 : 1);
                expression = expression.replaceFirst(firstBooleanValue + "\\\\/" + secondBooleanValue, String.valueOf(firstBooleanValue |
                        secondBooleanValue));
            }
            return String.valueOf(Long.parseLong(expression));
        } else return super.parse(expression);

    }

    private String removeDecimalPart(String expression) {
        return expression.replaceAll("\\.0", "");
    }
}
