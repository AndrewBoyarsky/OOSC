package com.hessky.oosc.lab7;

import java.util.Arrays;

/**
 * 2. Додати можливість наближеного обчислення арифметичних виразів над дійсними числами,
 * заданими десятковим записом, в якому ціла частина відділяється від дробової крапкою,
 * наприклад,
 * (1.0 + 2.5) * 3
 */
public class Task2 extends Task1 implements CalculatorTask {
    @Override
    public String parse(String expression) {
        for (int i = 0; i < expression.length(); i++) {

            if (expression.substring(i, i + 1).equals("/") || expression.substring(i, i + 1).equals("*")) {
                String firstOperand = "";
                String secondOperand = "";
                int firstOperandCharsCounter = 1;
                int secondOperandCharsCounter = 1;
                while (i - firstOperandCharsCounter >= 0 && (firstOperand = expression.substring(i - firstOperandCharsCounter, i)).matches
                        ("[0-9.]+")) {
                    firstOperandCharsCounter++;

                }
                if (!firstOperand.matches("[0-9.]+")) {
                    firstOperand = firstOperand.substring(1);
                }
                while (i + secondOperandCharsCounter + 1 <= expression.length() && (secondOperand = expression.substring(i + 1, i + 1 + secondOperandCharsCounter))
                        .matches
                                ("-?[0-9.]*")) {
                    secondOperandCharsCounter++;
                }
                if (!secondOperand.matches("-?[0-9.]*")) {
                    secondOperand = secondOperand.substring(0, secondOperand.length() - 1);
                }
                double firstNumber = Double.parseDouble(firstOperand);
                double secondNumber = Double.parseDouble(secondOperand);
                double res;
                if (expression.substring(i, i + 1).equals("/")) {
                    res = firstNumber / secondNumber;
                } else {
                    res = firstNumber * secondNumber;
                }

                expression = expression.replace(firstOperand + expression.substring(i, i + 1) + secondOperand, Double.toString(res));
                int resLength = Double.toString(res).length();
                int expressionLength = secondOperand.length() + 1 + firstOperand.length();
                i = i - expressionLength + resLength - 1;
                i = i < -1 ? -1 : i;
                expression = collapseSigns(expression);
            }
        }
        String temp = expression.replaceAll("[(]", "");
        temp = temp.replaceAll("[)]", "");
        String[] operators = Arrays.stream(temp.split("[0-9.]+")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        String[] operands = Arrays.stream(temp.trim().split("[-+]")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        double m = 0;
        int counter = 0;
        if (operands.length == operators.length && operators[0].equals("-")) {
            m = -Double.parseDouble(operands[0]);
        } else if (operands.length != 0) {
            m += Double.parseDouble(operands[0]);
            counter++;
        }
        for (int i = 1; i < operands.length; i++) {
            double t = Double.parseDouble(operands[i]);
            switch (operators[i - counter]) {
                case "+":
                    m += t;
                    break;
                case "-":
                    m -= t;
                    break;
            }
        }
        return String.valueOf(m);
    }
}
