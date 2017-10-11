package com.hessky.oosc.lab7;

public class Polynomial implements Cloneable {
    private double[] coef;
    private int degree;

    public Polynomial(double a, int b) {
        if (b < 0) {
            throw new IllegalArgumentException("exponent cannot be negative: " + b);
        }
        coef = new double[b + 1];
        coef[b] = a;
        reduce();
    }

    public static Polynomial fromString(String expression) {

        double coef;
        int degree;
        if (expression.contains("x^")) {
            degree = Integer.parseInt(expression.substring(expression.indexOf("x^") + 2).trim());
            if (expression.startsWith("x")) {
                coef = 1;
            } else {
                coef = Double.parseDouble(expression.substring(0, expression.indexOf("x^")).trim());
            }
        } else {
            coef = Double.parseDouble(expression);
            degree = 0;
        }
        return new Polynomial(coef, degree);
//
    }

    private void reduce() {
        degree = 0;
        for (int i = coef.length - 1; i >= 0; i--) {
            if (coef[i] != 0) {
                degree = i;
                return;
            }
        }
    }

    public int degree() {
        return degree;
    }

    public Polynomial plus(Polynomial that) {
        Polynomial poly = new Polynomial(0, Math.max(this.degree, that.degree));
        for (int i = 0; i <= this.degree; i++) poly.coef[i] += this.coef[i];
        for (int i = 0; i <= that.degree; i++) poly.coef[i] += that.coef[i];
        poly.reduce();
        return poly;
    }


    public Polynomial minus(Polynomial that) {
        Polynomial poly = new Polynomial(0, Math.max(this.degree, that.degree));
        for (int i = 0; i <= this.degree; i++)
            poly.coef[i] += this.coef[i];
        for (int i = 0; i <= that.degree; i++)
            poly.coef[i] -= that.coef[i];
        poly.reduce();
        return poly;
    }

    public Polynomial multiply(Polynomial that) {
        Polynomial poly = new Polynomial(0, this.degree + that.degree);
        for (int i = 0; i <= this.degree; i++)
            for (int j = 0; j <= that.degree; j++)
                poly.coef[i + j] += (this.coef[i] * that.coef[j]);
        poly.reduce();
        return poly;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Polynomial that = (Polynomial) other;
        if (this.degree != that.degree) return false;
        for (int i = this.degree; i >= 0; i--)
            if (this.coef[i] != that.coef[i]) return false;
        return true;
    }

    public Polynomial divide(Polynomial p) {
        if (degree == 0 && p.degree == 0) {
            return new Polynomial(coef[0] / p.coef[0], 0);
        } else if (p.degree == 0) {
            return this.multiply(new Polynomial(1 / p.coef[0], 0));
        }
        if (degree < p.degree) {
            return new Polynomial(0, 0);
        } else if (degree > p.degree) {

            int[] firstDegrees = new int[degree + 1];
            for (int i = coef.length - 1; i >= 0; i--) {
                if (coef[i] != 0) {
                    firstDegrees[degree - i] = i;
                }
            }

            int[] secondDegrees = new int[p.degree + 1];
            for (int i = p.coef.length - 1; i >= 0; i--) {
                if (p.coef[i] != 0) {
                    secondDegrees[p.degree - i] = i;
                }
            }
            Polynomial firstPartialPolynomial = null;
            try {
                firstPartialPolynomial = this.clone();
            }
            catch (CloneNotSupportedException ignored) {

            }
            Polynomial result = new Polynomial(0, 0);
            while (firstPartialPolynomial.degree >= p.degree) {

                int multiplierDegree = firstPartialPolynomial.degree - secondDegrees[0];
                Polynomial multiplierPolynomial = new Polynomial(firstPartialPolynomial.coef[firstPartialPolynomial.degree] / p.coef[p.degree],
                        multiplierDegree);
                Polynomial temp = p.multiply(multiplierPolynomial);
                firstPartialPolynomial = firstPartialPolynomial.minus(temp);
                result = result.plus(multiplierPolynomial);
            }
            return result;
        } else {
            return new Polynomial(coef[degree] / p.coef[p.degree], 0);
        }
    }

    @Override
    public Polynomial clone() throws CloneNotSupportedException {
        Polynomial p = (Polynomial) super.clone();
        p.coef = coef.clone();
        return p;
    }

    @Override
    public String toString() {
        if (degree == -1) return "0";
        else if (degree == 0) return "" + coef[0];
        else if (degree == 1) return coef[1] + "x" + (coef[0] == 0d ? "" : coef[0] < 0 ? "" + coef[0] : "+" + coef[0]);
        String s = coef[degree] + "x^" + degree;
        for (int i = degree - 1; i > 0; i--) {
            if (coef[i] == 0) continue;
            s = s + (coef[i] > 0 ? ("+" + coef[i]) : coef[i]) + "x^" + i;
        }
        if (coef[0] != 0.0) {
            s += coef[0] > 0 ? "+" + coef[0] : coef[0];
        }
        return s;
    }

}
