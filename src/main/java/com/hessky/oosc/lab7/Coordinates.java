package com.hessky.oosc.lab7;

public class Coordinates {
    protected int x1;
    protected int x2;

    public Coordinates(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;

        Coordinates that = (Coordinates) o;

        if (getX1() != that.getX1()) return false;
        return getX2() == that.getX2();
    }

    @Override
    public int hashCode() {
        int result = getX1();
        result = 31 * result + getX2();
        return result;
    }

    public Coordinates() {
    }

    @Override
    public String toString() {
        return x1 +
                " " + x2;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }
}