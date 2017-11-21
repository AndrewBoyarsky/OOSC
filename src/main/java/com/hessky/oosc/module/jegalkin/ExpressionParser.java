package com.hessky.oosc.module.jegalkin;

import java.util.*;

public class ExpressionParser {
    public static boolean flag = true;
    private static String operators = "+*";
    private static String delimiters = "() " + operators;

    private static boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isFunction(String token) {
        if (token.equals("sqrt") || token.equals("cube") || token.equals("pow10")) return true;
        return false;
    }

    private static int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("*")) return 2;
        if (token.equals("+")) return 3;
        return 4;
    }

    public static List<String> parse(String infix) {
        List<String> postfix = new ArrayList<String>();
        Deque<String> stack = new ArrayDeque<String>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                System.out.println("Некорректное выражение.");
                flag = false;
                return postfix;
            }
            if (curr.equals(" ")) continue;
            if (isFunction(curr)) stack.push(curr);
            else if (isDelimiter(curr)) {
                if (curr.equals("(")) stack.push(curr);
                else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Скобки не согласованы.");
                            flag = false;
                            return postfix;
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } else {
                    if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                        // унарный минус
                        curr = "u-";
                    } else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }

                    }
                    stack.push(curr);
                }

            } else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                System.out.println("Скобки не согласованы.");
                flag = false;
                return postfix;
            }
        }
        return postfix;
    }
}

class Ideone {
    public static Integer calc(List<String> postfix) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (String x : postfix) {
            if (x.equals("+")) stack.push(stack.pop() ^ stack.pop());
            else if (x.equals("*")) stack.push(stack.pop() & stack.pop());
            else stack.push(Integer.valueOf(x));
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first polynomial in order: a+b*c +1*(x+c):a b c x");
        String firstPolynomial = "(a+b)*c:a b c";//in.nextLine();
        String paramsfirstPolynomial = firstPolynomial.substring(firstPolynomial.indexOf(":") + 1);
        firstPolynomial = firstPolynomial.substring(0, firstPolynomial.indexOf(":"));
        System.out.println("Enter first polynomial in order: a+b*c +1*(x+c)");
        String secondPolynomial = "a*(a+b)";//in.nextLine();
        ExpressionParser n = new ExpressionParser();
        List<String> firstExpression = n.parse(firstPolynomial);
        List<String> secondExpression = n.parse(secondPolynomial);
        String[] params = paramsfirstPolynomial.split(" ");
        int[] bits = new int[params.length];
        if (bits.length != 0) {
            bits[0] = 1;
        }
        for (int i = 1; i < bits.length; i++) {
            bits[i] = bits[i - 1] * 2;
        }
        StringBuilder head = new StringBuilder();
        for (int i = 0; i < firstExpression.size(); i++) {
            if (paramsfirstPolynomial.contains(firstExpression.get(i))) {
                head.append(firstExpression.get(i));
            }
        }
        System.out.printf("%8.8s f s\n", head.reverse().toString().trim());
        boolean isEquals = true;
        for (int i = 0; i < Math.pow(params.length, 2) - 1; i++) {
//            for (String x : expression) System.out.print(x + " ");
            List<String> fExpr = new ArrayList<>();
            fExpr.addAll(firstExpression);
            List<String> sExpr = new ArrayList<>();
            sExpr.addAll(secondExpression);
            for (int j = 0; j < bits.length; j++) {
                int finalI = i;
                int finalJ = j;
                fExpr.replaceAll(s -> s.equals(params[finalJ]) ? (finalI & bits[finalJ]) == 0 ? "0" : "1" : s);
                sExpr.replaceAll(s -> s.equals(params[finalJ]) ? (finalI & bits[finalJ]) == 0 ? "0" : "1" : s);
            }
            String binaryString = Integer.toBinaryString(i);
            while (binaryString.length() < params.length) {
                binaryString = "0" + binaryString;
            }
            Integer fResult = calc(fExpr);
            Integer sResult = calc(sExpr);
            if (!Objects.equals(fResult, sResult)) {
                isEquals = false;
            }
            System.out.printf("%8.8s %1d %1d\n", binaryString, fResult, sResult);
        }
        if (isEquals) {
            System.out.println(firstPolynomial + " and " + secondPolynomial + " are EQUALS!");
        } else {
            System.out.println(firstPolynomial + " and " + secondPolynomial + " are NOT EQUALS!");
        }
    }
}
