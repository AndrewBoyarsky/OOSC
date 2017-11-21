package com.hessky.oosc.module.matrixcalc;

import Jama.Matrix;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        double[][] mat = new double[50][50];
        Random r = new Random();
        while (true) {

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    mat[i][j] = r.nextInt(2) - 1;
                }
            }
            Matrix m = new Matrix(mat);
            System.out.println(m.det());
            com.hessky.oosc.module.matrixcalc.Matrix matrix = new com.hessky.oosc.module.matrixcalc.Matrix(50, 50);
            matrix.fillWithRandomValues();
            matrix.det();
        }
    }
}
