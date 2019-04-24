package com.hessky.oosc.lab7;

import java.util.Objects;

public class Task7 extends Task6 implements CalculatorTask {
    private boolean isMatrix;

    private void checkIsMatrix(String expression) {
        isMatrix = expression.contains("[") && expression.contains("]");
    }

    @Override
    public String evaluate(String expression) {
        Objects.requireNonNull(expression, "String must be not null");
        if (expression.isEmpty()) return "";
        checkIsMatrix(expression);
        return super.evaluate(expression);
    }

    @Override
    public String parse(String expression) {
        if (isMatrix) {
            while (expression.contains("inv")) {
                int invIndex = expression.indexOf("inv");
                int firstMatrixIndex = invIndex + 3;
                int secondMatrixIndex = expression.substring(invIndex).indexOf("]") + 1 + invIndex;
                String matrixString = expression.substring(firstMatrixIndex, secondMatrixIndex);
                double[][] matrix = parseMatrix(matrixString);
                double[][] invMatrix = matrix.clone();
                inversion(invMatrix, invMatrix.length);
                expression = expression.substring(0, invIndex) + matrixToString(invMatrix) + (secondMatrixIndex >= expression.length() ?
                        "" : expression.substring(secondMatrixIndex + 1));
            }
            while (expression.contains("*")) {
                int multyplyingIndex = expression.indexOf("*");
                int firstMatrixOpenIndex = expression.substring(0, multyplyingIndex).lastIndexOf("[");
                int firstMatrixCloseIndex = multyplyingIndex;
                String firstMatrixString = expression.substring(firstMatrixOpenIndex, firstMatrixCloseIndex);
                int secondMatrixOpenIndex = multyplyingIndex + 1;
                int secondMatrixCloseIndex = expression.substring(multyplyingIndex + 1).indexOf("]") + 2 + multyplyingIndex;
                String secondMatrixString = expression.substring(secondMatrixOpenIndex, secondMatrixCloseIndex);
                double[][] firstMatrix = parseMatrix(firstMatrixString);
                double[][] secondMatrix = parseMatrix(secondMatrixString);

                if (expression.charAt(multyplyingIndex + 1) == '-') {
                    secondMatrix = changeMatrixSign(secondMatrix);
                    secondMatrixCloseIndex++;
                }
                if (expression.substring(0, multyplyingIndex).lastIndexOf("[") == expression.substring(0, multyplyingIndex).indexOf("[") &&
                        expression.charAt(0) == '-') {
                    firstMatrix = changeMatrixSign(firstMatrix);
                    firstMatrixOpenIndex--;
                }
                expression = expression.substring(0, firstMatrixOpenIndex) + matrixToString(multiplyMatrices(firstMatrix, secondMatrix)) +
                        (secondMatrixCloseIndex >= expression.length() ? "" : expression.substring(secondMatrixCloseIndex - 1));
            }
            expression = collapseSigns(expression);
            while (expression.contains("+")) {
                int addIndex = expression.indexOf("+");
                int firstMatrixOpenIndex = expression.substring(0, addIndex).lastIndexOf("[");
                int firstMatrixCloseIndex = addIndex;
                String firstMatrixString = expression.substring(firstMatrixOpenIndex, firstMatrixCloseIndex);
                int secondMatrixOpenIndex = addIndex + 1;
                int secondMatrixCloseIndex = expression.substring(addIndex + 1).indexOf("]") + 2 + addIndex;
                String secondMatrixString = expression.substring(secondMatrixOpenIndex, secondMatrixCloseIndex);
                double[][] firstMatrix = parseMatrix(firstMatrixString);
                double[][] secondMatrix = parseMatrix(secondMatrixString);
                if (expression.charAt(addIndex + 1) == '-') {
                    secondMatrix = changeMatrixSign(secondMatrix);
                    secondMatrixCloseIndex++;
                }
                if (expression.substring(0, addIndex).lastIndexOf("[") == expression.substring(0, addIndex).indexOf("[") &&
                        expression.charAt(0) == '-') {
                    firstMatrix = changeMatrixSign(firstMatrix);
                    firstMatrixOpenIndex--;
                }
                expression = expression.substring(0, firstMatrixOpenIndex) + matrixToString(addMatrices(firstMatrix, secondMatrix)) +
                        (secondMatrixCloseIndex >= expression.length() ? "" : expression.substring(secondMatrixCloseIndex - 1));
            }

            expression = collapseSigns(expression);
            if (expression.startsWith("-") && expression.indexOf("]") == expression.length() - 1) {
                double[][] matrix = parseMatrix(expression.substring(1));
                changeMatrixSign(matrix);
                expression = matrixToString(matrix);
            } else {
                while (expression.contains("]-[")) {
                    int minusIndex = expression.indexOf("]-[") + 1;
                    int firstMatrixOpenIndex = expression.substring(0, minusIndex).lastIndexOf("[");
                    int firstMatrixCloseIndex = minusIndex;
                    String firstMatrixString = expression.substring(firstMatrixOpenIndex, firstMatrixCloseIndex);
                    int secondMatrixOpenIndex = minusIndex + 1;
                    int secondMatrixCloseIndex = expression.substring(secondMatrixOpenIndex).indexOf("]") + 2 + minusIndex;
                    String secondMatrixString = expression.substring(secondMatrixOpenIndex, secondMatrixCloseIndex);
                    double[][] firstMatrix = parseMatrix(firstMatrixString);
                    double[][] secondMatrix = parseMatrix(secondMatrixString);
                    if (expression.substring(0, minusIndex).lastIndexOf("[") == expression.substring(0, minusIndex).indexOf("[") &&
                            expression.charAt(0) == '-') {
                        firstMatrix = changeMatrixSign(firstMatrix);
                        firstMatrixOpenIndex--;
                    }
                    expression = expression.substring(0, firstMatrixOpenIndex) + matrixToString(addMatrices(firstMatrix, changeMatrixSign
                            (secondMatrix))) + (secondMatrixCloseIndex >= expression.length() ? "" : expression.substring(secondMatrixCloseIndex -
                            1));
                }
            }
            return expression;

        }
        return super.parse(expression);
    }

    public double[][] changeMatrixSign(double[][] matrix) {
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                aMatrix[j] = -aMatrix[j];
            }
        }
        return matrix;
    }

    private String matrixToString(double[][] matrix) {
        StringBuilder s = new StringBuilder(100);
        s.append("[");
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                s.append(aMatrix[j]).append(" ");
            }
            s.append("; ");
        }
        s.append("]");
        return s.toString();
    }

    private double[][] multiplyMatrices(double[][] first, double[][] second) {
        if (second.length != first[0].length) {
            throw new UnsupportedOperationException("Matrices connot be multiplied");
        }
        double[][] result = new double[first.length][second[0].length];
        for (int k = 0; k < first.length; k++) {
            for (int i = 0; i < second[0].length; i++) {
                double res = 0;
                for (int j = 0; j < first[0].length; j++) {
                    res += first[k][j] * second[j][i];
                }
                result[k][i] = res;
            }
        }
        return result;
    }

    private double[][] parseMatrix(String s) {

        String temp = s.substring(s.indexOf("[") + 1, s.lastIndexOf("]")).trim();
        String[] rows = temp.split(";");
        double[][] result = new double[rows.length][rows[0].split(" ").length];
        for (int i = 0; i < rows.length; i++) {
            String[] rowElements = rows[i].trim().split(" ");
            for (int j = 0; j < rowElements.length; j++) {
                result[i][j] = Double.parseDouble(rowElements[j]);
            }
        }
        return result;
    }

//    private double[][] multiplyMatrix(int constant, double[][] matrix) {
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                matrix[i][j] = constant * matrix[i][j];
//            }
//        }
//        return matrix;
//    }

    private double[][] addMatrices(double[][] first, double[][] second) {
        if (first.length != second.length || first[0].length != second[0].length) {
            throw new UnsupportedOperationException("Matrices cannot be added!");
        }
        double[][] result = new double[first.length][first[0].length];
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[0].length; j++) {
                result[i][j] = first[i][j] + second[i][j];
            }
        }
        return result;
    }

    private void inversion(double[][] A, int N) {
        double temp;

        double[][] E = new double[N][N];


        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                E[i][j] = 0f;

                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < N; k++) {
            temp = A[k][k];

            for (int j = 0; j < N; j++) {
                A[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < N; i++) {
                temp = A[i][k];

                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = N - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = A[i][k];

                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = E[i][j];

    }
}
