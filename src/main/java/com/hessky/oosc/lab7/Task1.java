package com.hessky.oosc.lab7;

import java.util.Arrays;
import java.util.Objects;

/**
 1. Використовуючи принципи об'єктно-орієнтованого програмування написати на мові C#
 програму, яка дозволяє обчислювати значення введених користувачем арифметичних виразів,
 що можуть включати цілі числа в діапазоні -2^31...2^31-1, задані десятковим записом,
 унарну операцію мінус -, бінарні операції додавання +, віднімання -, множення *, ділення
 /, та круглі дужки ( ). Наприклад, якщо у якості вхідних даних програми ввести
 1 + (2*3 - 10/2)
 приграма має вивести 2 в якості результату.
 Операція ділення розуміється як ціла частина від ділення двох цілих чисел. Прогалини у
 виразі між числами, дужками та знаками операцій ігноруються.
 */

public class Task1 implements CalculatorTask {
    public Coordinates findFirstPriorityBraces(String expression, int braceSeekStartPosition) {
        Objects.requireNonNull(expression);

        int x1 = expression.indexOf("(");
        int x2;
        int openBraces = x1;
        int closeBraces;
        closeBraces = openBraces + 1 + expression.substring(openBraces + 1).indexOf(")"); //(2*(1+2)+2*(3/2))
        openBraces = closeBraces + 1 + expression.substring(closeBraces + 1).indexOf("("); //(2*(1+2)+2*(3/2))
        x2 = closeBraces;
        if (openBraces > closeBraces) {
            return new Coordinates(x1 + braceSeekStartPosition, x2 + braceSeekStartPosition);
        }

        if (x1 == -1 || x2 == -1) {
            return null;
        }
        if (expression.substring(x1 + 1, x2).contains("(") && expression.substring(x1 + 1, x2 + 1).contains(")")) {
            braceSeekStartPosition += x1;
            Coordinates c = findFirstPriorityBraces(expression.substring(x1 + 1, x2 + 1), braceSeekStartPosition + 1);
            return new Coordinates(c.x1, c.x2);
        } else {
            return new Coordinates(x1 + braceSeekStartPosition, x2 + braceSeekStartPosition);
        }


    }

    public String evaluate(String expression) {
        String temp = expression;
        if (!temp.contains("[") && !temp.contains("]")) {
            temp = temp.replaceAll(" ", "");
        } else {
            int i = 0;
            while (i < temp.length() && temp.substring(i).contains("[")) {
                int openMatrixBrace = temp.substring(i).indexOf("[") + i;
                int closeMatrixBrace = temp.substring(i).indexOf("]") + i;
                temp = temp.substring(0, i) + temp.substring(i, openMatrixBrace).replaceAll(" ", "") + temp.substring
                        (openMatrixBrace).trim();
                i = closeMatrixBrace + 1;
            }
        }
        Coordinates c;
        while ((c = findFirstPriorityBraces(temp, 0)) != null) {

            temp = temp.substring(0, c.getX1()) + parse(temp.substring(c.getX1() + 1, c.getX2())) + temp.substring(c.getX2() + 1);
            temp = collapseSigns(temp);
        }
        return parse(temp);
    }

    public String collapseSigns(String temp) {
        String[] signs = temp.split("[0-9().]");
        for (String sign : signs) {
            if (sign.length() > 1) {
                switch (sign) {
                    case "-+":
                        temp = temp.replaceFirst("-\\+", "-");
                        break;
                    case "+-":
                        temp = temp.replaceFirst("\\+-", "-");
                        break;
                    case "++":
                        temp = temp.replaceFirst("\\+\\+", "+");
                        break;
                    case "--":
                        if (temp.startsWith("--")) {
                            temp = temp.replaceFirst("--", "");
                        } else {
                            temp = temp.replaceFirst("--", "+");
                        }
                        break;
                }
            }
        }
        return temp;
    }

    public String parse(String expression) {
        for (int i = 0; i < expression.length(); i++) {

            if (expression.substring(i, i + 1).equals("/") || expression.substring(i, i + 1).equals("*")) {
                String firstOperand = "";
                String secondOperand = "";
                int firstOperandCharsCounter = 1;
                int secondOperandCharsCounter = 1;
                while (i - firstOperandCharsCounter >= 0 && (firstOperand = expression.substring(i - firstOperandCharsCounter, i)).matches("[0-9]+")) {
                    firstOperandCharsCounter++;

                }
                if (!firstOperand.matches("[0-9]+")) {
                    firstOperand = firstOperand.substring(1);
                }
                while (i + secondOperandCharsCounter + 1 <= expression.length() && (secondOperand = expression.substring(i + 1, i + 1 + secondOperandCharsCounter))
                        .matches
                                ("-?[0-9]*")) {
                    secondOperandCharsCounter++;
                }
                if (!secondOperand.matches("-?[0-9]*")) {
                    secondOperand = secondOperand.substring(0, secondOperand.length() - 1);
                }
                long firstNumber = Long.parseLong(firstOperand);
                long secondNumber = Long.parseLong(secondOperand);
                long res;
                if (expression.substring(i, i + 1).equals("/")) {
                    res = firstNumber / secondNumber;
                } else {
                    res = firstNumber * secondNumber;
                }

                expression = expression.replace(firstOperand + expression.substring(i, i + 1) + secondOperand, Long.toString(res));
                int resLength = Long.toString(res).length();
                int expressionLength = secondOperand.length() + 1 + firstOperand.length();
                i = i - expressionLength + resLength - 1;
                i = i < -1 ? -1 : i;
                expression = collapseSigns(expression);
            }
        }
        String temp = expression.replaceAll("[(]", "");
        temp = temp.replaceAll("[)]", "");
        String[] operators = Arrays.stream(temp.split("[0-9]+")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        String[] operands = Arrays.stream(temp.trim().split("[-+]")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        long m = 0;
        int counter = 0;
        if (operands.length == operators.length && operators[0].equals("-")) {
            m = -Long.parseLong(operands[0]);
        } else if (operands.length != 0) {
            m += Long.parseLong(operands[0]);
            counter++;
        }
        for (int i = 1; i < operands.length; i++) {
            long t = Long.parseLong(operands[i]);
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


