package com.hessky.oosc.lab7;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 8. Додати можливість точного обчислення значень виразів над многочленами однієї змінної х
 * степеня не більше 100 з цілими коефіцієнтами в діапазоні -2^31...2^31-1. Вирази можуть
 * включати одночлени x^n (n=1...100), цілі константи в межах -2^31...2^31-1, задані
 * десятковим записом, бінарні операції додавання +, віднімання -, множення *, та ділення / (
 * яка розуміється як частка від ділення двох многочленів) унарні операції + та -, та круглі
 * дужки ( ). Результом обчислення має бути запис многочлену, що є результатом виконання
 * операцій вказаних у вхідному виразі, вигляду:
 * a0 + a1 * x^1 + a2 * x^2 + ...
 * де a0, a1, a2, ... - десяткові записи цілих чисел.
 * Наприклад, якщо на вхід програмі подати вираз
 * x^100 + (5 + x^1) * x^3
 * вона має вивести
 * 0 + 0 * x^1 + 0 * x^2 + 5 * x^3 + 1 * x^4 + 1 * x^100
 */
public class Task8 extends Task7 implements CalculatorTask {
    private boolean isPolynomial;

    private void checkIsPolynomial(String expression) {
        isPolynomial = expression.contains("x^");
    }

    private Coordinates findInnerBraces(String expression) {
        char[] chars = expression.toCharArray();
        int openCounter = 1;
        int closeCounter = 0;
        int openPosition = expression.indexOf("(");
        if (openPosition == -1) {
            return null;
        }
        int closePosition = 0;
        for (int i = openPosition + 1; i < chars.length; i++) {
            if (closeCounter == openCounter) {
                break;
            }
            if (chars[i] == '(') {
                openCounter++;
            } else if (chars[i] == ')') {
                closeCounter++;
                closePosition = i;
            }
        }
        if (closePosition == 0) {
            return null;
        }
        return new Coordinates(openPosition, closePosition);
    }

    @Override
    public String evaluate(String expression) {
        Objects.requireNonNull(expression, "String must be not null");
        if (expression.isEmpty()) return "";
        try {

            checkIsPolynomial(expression);
            if (isPolynomial) {
                expression = expression.replaceAll(" ", "");
                Coordinates c;
                while ((c = findExpression(expression)) != null) {
                    expression = expression.substring(0, c.x1) + parse(expression.substring(c.x1, c.x2) + expression.substring(c.x2));
                }
                return parse(expression);
            }
            return super.evaluate(expression);
        }
        catch (Throwable e) {
            throw new IllegalArgumentException("Expression contains errors! " + expression, e);
        }
    }

    private Polynomial calculate(String expression, Map<String, Polynomial> polynomialMap) {
        Map<String, Polynomial> polynomials = new HashMap();
        int pCounter = 0;
        while (expression.contains("/") || expression.contains("*")) {
            int divisionIndex = expression.indexOf("/");
            int multiplyIndex = expression.indexOf("*");
            int signIndex;
            if (divisionIndex == -1) {
                signIndex = multiplyIndex;
            } else if (multiplyIndex == -1) {
                signIndex = divisionIndex;
            } else {
                signIndex = Math.min(multiplyIndex, divisionIndex);
            }

            int minusIndex = expression.substring(0, signIndex).lastIndexOf("-");
            int plusIndex = expression.substring(0, signIndex).lastIndexOf("+");
            int fMultiplyIndex = expression.substring(0, signIndex).lastIndexOf("*");
            int fDivisionIndex = expression.substring(0, signIndex).lastIndexOf("/");
            int nearSignIndex = Math.max(plusIndex, Math.max(fMultiplyIndex, Math.max(fDivisionIndex, minusIndex)));
            if (nearSignIndex == -1) nearSignIndex = 0;
            String firstPol = expression.substring(nearSignIndex == 0 ? nearSignIndex : nearSignIndex + 1, signIndex);
            Polynomial first = initPolynomial(firstPol, polynomialMap, polynomials);


            minusIndex = expression.substring(signIndex + 1).indexOf("-");
            plusIndex = expression.substring(signIndex + 1).indexOf("+");
            fMultiplyIndex = expression.substring(signIndex + 1).indexOf("*");
            fDivisionIndex = expression.substring(signIndex + 1).indexOf("/");
            int farSignIndex = Math.min(plusIndex == -1 ? Integer.MAX_VALUE : plusIndex,
                    Math.min(fMultiplyIndex == -1 ? Integer.MAX_VALUE : fMultiplyIndex,
                            Math.min(fDivisionIndex == -1 ? Integer.MAX_VALUE : fDivisionIndex,
                                    minusIndex == -1 ? Integer.MAX_VALUE : minusIndex)));
            if (farSignIndex == Integer.MAX_VALUE) farSignIndex = expression.length();
            else farSignIndex += signIndex + 1;

            String secondPol = expression.substring(signIndex + 1, farSignIndex);
            Polynomial second = initPolynomial(secondPol, polynomialMap, polynomials);

            Polynomial result;
            if (signIndex == multiplyIndex) {
                result = first.multiply(second);
            } else {
                result = first.divide(second);
            }
            expression = expression.substring(0, nearSignIndex == 0 ? 0 : nearSignIndex + 1) + "R" + pCounter + expression.substring(farSignIndex);
            expression = collapseSigns(expression);
            polynomials.put("R" + pCounter++, result);
        }
        String[] pols = Arrays.stream(expression.split("[-+]")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        String[] polSigns = Arrays.stream(expression.split("[x^0-9.PR]")).filter(e -> !e.isEmpty()).toArray(String[]::new);
        Polynomial res = new Polynomial(0, 0);
        int minusCounter = 0;
        if (polSigns.length == pols.length && expression.startsWith("-")) {
            res = res.minus(initPolynomial(pols[0], polynomialMap, polynomials));
        } else {
            res = res.plus(initPolynomial(pols[0], polynomialMap, polynomials));
            minusCounter = 1;
        }

        for (int i = 1; i < pols.length; i++) {
            String sign = polSigns[i - minusCounter];
            Polynomial p = initPolynomial(pols[i], polynomialMap, polynomials);
            switch (sign) {
                case "-":
                    res = res.minus(p);
                    break;
                case "+":
                    res = res.plus(p);
                    break;
            }
        }
        return res;
    }

    private Polynomial initPolynomial(String polynomialString, Map<String, Polynomial> polynomialMap, Map<String, Polynomial> polynomials) {
        Polynomial res = null;
        if (polynomialString.contains("P")) {
            if (polynomialMap != null && polynomialMap.get(polynomialString) != null) {
                res = polynomialMap.get(polynomialString);
            }
        } else if (polynomialString.contains("R")) {
            res = polynomials.get(polynomialString);
        } else {
            res = Polynomial.fromString(polynomialString);
        }
        return res;
    }

    @Override
    public String parse(String expression) {
        if (isPolynomial) {
            Coordinates c;
            Map<String, Polynomial> inBracesPolynomials = new HashMap<>();
            int pCounter = 0;
            while ((c = findInnerBraces(expression)) != null) {
                int ic = 0;
                while (expression.substring(c.x1 + 1, c.x2).contains("(")) {
                    ic += c.x1 + 1;
                    c = findInnerBraces(expression.substring(c.x1 + 1, c.x2));
                    c = new Coordinates(c.x1 + ic, c.x2 + ic);
                }
                String polynomialExpression = expression.substring(c.x1 + 1, c.x2);
                inBracesPolynomials.put("P" + pCounter, calculate(polynomialExpression, inBracesPolynomials));
                expression = expression.substring(0, c.x1) + "P" + pCounter++ + expression.substring(c.x2 + 1);
                expression = collapseSigns(expression);
            }
            Polynomial res = calculate(expression, inBracesPolynomials);
            return res.toString();

        } else return super.parse(expression);
    }

    public Coordinates findExpression(String expr) {
        char[] chars = expr.toCharArray();

        Coordinates c = findInnerBraces(expr);
        int x1 = -1;
        int x2 = -1;
        if (c != null) {
            x1 = c.x1;
            x2 = c.x2;
            for (int i = c.x1 - 1; i >= 0; i--) {
                if (chars[i] == '+' || chars[i] == '-') {
                    x1 = i + 1;
                }
            }
            int openCounter = 0;
            for (int i = c.x2 + 1; i < chars.length; i++) {
                if (chars[i] == '(') {
                    openCounter++;
                } else if (chars[i] == ')') {
                    openCounter--;
                }
                if (openCounter > 0) {
                    continue;
                }
                if (chars[i] == '+' || chars[i] == '-') {
                    x2 = i;
                }
                if (x2 == c.x2 && i == chars.length - 1) {
                    x2 = i;
                }
            }
        }
        if (x1 == -1 || x2 == -1 || x2 == 0) {
            return null;
        }
        return new Coordinates(x1, x2);
    }
}