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
    private static final int INITIAL_RADIUS = 5;
    private static int dotRadius = INITIAL_RADIUS;

    /**
     * Constructor needs x coordinate
     * and y coordinate of dot.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the dot
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the dot
     * @return y coordinate
     */
    public double getY() {
        return y;
    }

    public static void setRadius(int radius) {
        dotRadius = radius;
    }
    public static int getRadius(){
        return dotRadius;
    }

    /**
     * Helper method to scale the coordinate pair to a canvas
     * @param xFactor x scaling factor
     * @param yFactor y scaling factor
     */
    public void scaleCoordPair(double xFactor, double yFactor){
        x *= xFactor;
        y *= yFactor;
    }

}
