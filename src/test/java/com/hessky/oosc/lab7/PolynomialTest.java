package com.hessky.oosc.lab7;

import org.junit.Test;

public class PolynomialTest {
    @Test
    public void testDivision() throws CloneNotSupportedException {
        Polynomial p1 = new Polynomial(5, 4);
        Polynomial p2 = new Polynomial(-3, 3);
        Polynomial p3 = new Polynomial(-5, 2);
        Polynomial p4 = new Polynomial(4, 1);
        Polynomial p5 = new Polynomial(-2, 0);
        Polynomial f = p1.plus(p2).plus(p3).plus(p4).plus(p5);
        Polynomial s3 = new Polynomial(3, 2);
        Polynomial s4 = new Polynomial(6, 1);
        Polynomial s5 = new Polynomial(9, 0);
        Polynomial s = s3.plus(s4).plus(s5);
        Polynomial res = f.divide(s);

//        Assert.assertTrue();
    }
}
