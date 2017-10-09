package com.hessky.oosc.lab7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 4. Додати можливість наближеного обчислення виразів над дійсними числами, що можуть
 * включати арифметичні дії та застосування тригонометричних функцій sin, cos, tan, наприклад,
 * (1.5 + sin(2.0 + cos(3.5)))*3
 */
public class Task4 extends Task3 implements CalculatorTask {
    @Override
    public String parse(String expression) {
        Pattern cosPattern = Pattern.compile("cos-?\\d+\\.?\\d*");
        Pattern sinPattern = Pattern.compile("sin-?\\d+\\.?\\d*");
        Pattern tanPattern = Pattern.compile("tan-?\\d+\\.?\\d*");
        while (expression.contains("cos") || expression.contains("sin") || expression.contains("tan")) {

            Matcher cosMatcher = cosPattern.matcher(expression);
            if (cosMatcher.find()) {
                String cos = cosMatcher.group();
                expression = expression.replaceFirst(cos, String.valueOf(Math.cos(Double.parseDouble(cos.substring(cos.indexOf("cos") + 3)))));
            }
            Matcher sinMatcher = sinPattern.matcher(expression);
            if (sinMatcher.find()) {
                String sin = sinMatcher.group();
                expression = expression.replaceFirst(sin, String.valueOf(Math.sin(Double.parseDouble(sin.substring(sin.indexOf("sin") + 3)))));
            }
            Matcher tanMatcher = tanPattern.matcher(expression);
            if (tanMatcher.find()) {
                String tan = tanMatcher.group();
                expression = expression.replaceFirst(tan, String.valueOf(Math.tan(Double.parseDouble(tan.substring(tan.indexOf("tan") + 3)))));
            }
            expression = collapseSigns(expression);
        }
        return super.parse(expression);
    }
}
