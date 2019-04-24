package com.hessky.oosc.module.matrixcalc;

import java.math.BigInteger;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Matrix {
    private int[][] matrix;

    public Matrix(byte width, byte height) {
        matrix = new int[width][height];
    }

    public Matrix(int[][] matrix) {

        this.matrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = Arrays.copyOf(matrix[i], matrix[0].length);
        }
    }

    public Matrix(int width, int height, boolean isRandom) {
        this.matrix = new int[width][height];
//        fillWithRandomValues();
    }

    public int[][] getMatrix() {
        return matrix;
    }

//    public static Matrix parseMatrix(String s) {
//        String temp = s.substring(s.indexOf("[") + 1, s.lastIndexOf("]")).trim();
//        String[] rows = temp.split(";");
//        int[][] result = new int[rows.length][rows[0].split(" ").length];
//        for (int i = 0; i < rows.length; i++) {
//            String[] rowElements = rows[i].trim().split(" ");
//            for (int j = 0; j < rowElements.length; j++) {
//                result[i][j] = Integer.parseInt(rowElements[j]);
//            }
//        }
//        return new Matrix(result);
//    }

    public BigInteger det() {
        int[][] clonedMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            clonedMatrix[i] = Arrays.copyOf(matrix[i], matrix.length);
        }
        int n = clonedMatrix.length;
        for (int i = 0; i < n; i++) {
            // Search for maximum in this column
            double maxEl = abs(clonedMatrix[i][i]);
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (abs(clonedMatrix[k][i]) > maxEl) {
                    maxEl = abs(clonedMatrix[k][i]);
                    maxRow = k;
                }
            }

            // Swap maximum row with current row (column by column)
            for (int k = i; k < n; k++) {
                int tmp = clonedMatrix[maxRow][k];
                clonedMatrix[maxRow][k] = clonedMatrix[i][k];
                clonedMatrix[i][k] = tmp;
            }

            // Make all rows below this one 0 in current column
            for (int k = i + 1; k < n; k++) {
                double c = -clonedMatrix[k][i] / clonedMatrix[i][i];
                for (int j = i; j < n; j++) {
                    if (i == j) {
                        clonedMatrix[k][j] = 0;
                    } else {
                        clonedMatrix[k][j] += c * clonedMatrix[i][j];
                    }
                }
            }
        }
        BigInteger det = new BigInteger(String.valueOf(clonedMatrix[0][0]));
        for (int i = 1; i < clonedMatrix.length; i++) {
            det = det.multiply(new BigInteger(Integer.toString(clonedMatrix[i][i])));
        }
        return det;
    }

//    public Matrix fillWithRandomValues() {
//        Random random = new Random();
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                matrix[i][j] = random.nextInt(3) - 1;
//            }
//        }
//        return this;
//    }

//    public Matrix changeMatrixSign() {
//        for (int[] row : matrix) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                row[j] = -row[j];
//            }
//        }
//        return this;
//    }

//    @Override
//    public String toString() {
//        StringBuilder s = new StringBuilder(100);
//        s.append("[");
//        for (int[] row : matrix) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                s.append(row[j]).append(" ");
//            }
//            s.append(";\n");
//        }
//        s.append("]");
//        return s.toString();
//    }

    public Matrix multiplyMatrices(Matrix anotherMatrix) {
        if (anotherMatrix.getMatrix().length != matrix[0].length) {
            throw new UnsupportedOperationException("Matrices connot be multiplied");
        }
        int[][] result = new int[matrix.length][anotherMatrix.getMatrix()[0].length];
        for (int k = 0; k < matrix.length; k++) {
            for (int i = 0; i < anotherMatrix.getMatrix()[0].length; i++) {
                int res = 0;
                for (int j = 0; j < matrix[0].length; j++) {
                    res += matrix[k][j] * anotherMatrix.getMatrix()[j][i];
                }
                result[k][i] = res;
            }
        }
        return new Matrix(result);
    }

//    public Matrix multiplyMatrix(int constant) {
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                matrix[i][j] = constant * matrix[i][j];
//            }
//        }
//        return new Matrix(matrix);
//    }

    public Matrix addMatrices(Matrix anotherMatrix) {
        if (matrix.length != anotherMatrix.getMatrix().length || matrix[0].length != anotherMatrix.getMatrix()[0].length) {
            throw new UnsupportedOperationException("Matrices cannot be added!");
        }
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] + anotherMatrix.getMatrix()[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix transpose() {
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return new Matrix(result);
    }

//    private void inversion(int[][] A, int N) {
//        int temp;
//
//        int[][] E = new int[N][N];
//
//
//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++) {
//                E[i][j] = 0;
//
//                if (i == j)
//                    E[i][j] = 1;
//            }
//
//        for (int k = 0; k < N; k++) {
//            temp = A[k][k];
//
//            for (int j = 0; j < N; j++) {
//                A[k][j] /= temp;
//                E[k][j] /= temp;
//            }
//
//            for (int i = k + 1; i < N; i++) {
//                temp = A[i][k];
//
//                for (int j = 0; j < N; j++) {
//                    A[i][j] -= A[k][j] * temp;
//                    E[i][j] -= E[k][j] * temp;
//                }
//            }
//        }

//        for (int k = N - 1; k > 0; k--) {
//            for (int i = k - 1; i >= 0; i--) {
//                temp = A[i][k];
//
//                for (int j = 0; j < N; j++) {
//                    A[i][j] -= A[k][j] * temp;
//                    E[i][j] -= E[k][j] * temp;
//                }
//            }
//        }

//        for (int i = 0; i < N; i++)
//            for (int j = 0; j < N; j++)
//                A[i][j] = E[i][j];
//
//    }
}
