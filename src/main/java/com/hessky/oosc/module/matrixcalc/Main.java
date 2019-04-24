package com.hessky.oosc.module.matrixcalc;

import Jama.Matrix;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        double[][] mat = new double[50][50];
        Random r = new Random();
//        while (true) {

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    mat[i][j] = r.nextInt(2) - 1;
                }
            }
            Matrix m = new Matrix(mat);
            System.out.println(m.det());
//            com.hessky.oosc.module.matrixcalc.Matrix matrix = new com.hessky.oosc.module.matrixcalc.Matrix(50, 50);
            int[][] matd = {{1, 2}, {3,4}, {5,6}};
//            com.hessky.oosc.module.matrixcalc.Matrix matrix1 = new com.hessky.oosc.module.matrixcalc.Matrix(matd);
//            System.out.println(Arrays.deepToString(matrix1.transpose().getMatrix()));
        }
//    }
}
