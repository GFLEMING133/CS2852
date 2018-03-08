/*
 * CS2852
 * Spring 2018
 * Lab 1 Dot2Dot
 * Name: Grace Fleming
 * Createdc 3/5/2018
 */
package flemingg;

/**
 * Class representing a dot object.
 */
public class Dot {
    private double x;
    private double y;

    /**
     *
     * @param x
     * @param y
     */
    public Dot (double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

}
