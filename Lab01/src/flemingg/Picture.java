/*
 * CS2852
 * Spring 2018
 * Lab 1 Dot2Dot
 * Name: Grace Fleming
 * Createdc 3/5/2018
 */
package flemingg;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Class to hold a list of dots
 * and manage how the list is displayed.
 * @author flemingg
 * @version 1
 */

public class Picture {

    private double canvasHeight = 0;
    private double canvasWidth = 0;

    private static final int RADII_IN_DIAMETER = 2;
    private static final int X_POS_IN_ARRAY = 0;
    private static final int Y_POS_IN_ARRAY = 1;
    private Color color;
    private ArrayList<Dot> dots = new ArrayList<>();
    /**
     * Requires the height and width of the given canvas item
     * @param height for height of canvas
     * @param width width of canvas
     */
    public Picture(double width, double height) {
        this.canvasHeight = height;
        this.canvasWidth = width;
        this.color = Color.BLACK;
    }
    /**
     * Sets the radius of a circle
     * @param radius new radius of circle.
     */
    public void setDotRadius(int radius) {
        Dot.setRadius(radius);
    }
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Loads a .dot file, and displays a traditional dots and lines view.
     * @param file file to read points from
     * @throws InputMismatchException if the file contains
     *         data separated by commas but in an incorrect format
     *         (e.g., not in double form)
     * @throws IOException if file has error being opened
     */
    public void load(File file)
            throws IOException, InputMismatchException{
        FileReader io = new FileReader(file);
        BufferedReader reader = new BufferedReader(io);
        String currentLine = "";
        dots.clear();
        while(reader.ready()) {
            currentLine = reader.readLine();
            double[] dotXY = getCoordinatesFromLine(currentLine);
            Dot dotToAdd = new Dot(dotXY[X_POS_IN_ARRAY], dotXY[Y_POS_IN_ARRAY]);
            dotToAdd.scaleCoordPair(canvasWidth, canvasHeight);
            dots.add(dotToAdd);
        }
    }
    /**
     * Draws every dot in the list of dots on the canvas.
     * Dots are CENTERED on the point given by the dot.
     * @param canvas the canvas to draw on
     */
    public void drawDots(Canvas canvas) {
        GraphicsContext drawer = canvas.getGraphicsContext2D();
        applySettings(drawer);
        drawer.setStroke(color);
        for (Dot dot : dots) {
            double centeredXTranslator = canvasWidth - (dot.getX() + (Dot.getRadius()));
            double centeredYTranslator = canvasHeight - (dot.getY() + (Dot.getRadius()));
            drawer.setFill(color);
            drawer.fillOval(centeredXTranslator, centeredYTranslator,
                    Dot.getRadius() * RADII_IN_DIAMETER,
                    Dot.getRadius() * RADII_IN_DIAMETER);
        }
    }
    /**
     * Connects the dots on the canvas with lines.
     * @param canvas the canvas to draw on
     */
    public void drawLines(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        applySettings(context);
        context.moveTo(0, 0);
        context.beginPath();
        for(Dot dot : dots) {
            double canvasx = canvasWidth - dot.getX();
            double canvasy = canvasHeight - dot.getY();
            context.lineTo(canvasx, canvasy);
            context.stroke();
        }
        context.closePath();
    }
    /**
     * Connects the dots found in the dots list
     * with quadratic curves.
     * @param canvas the canvas to draw on
     *
     */
    public void drawQuadraticCurve(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        applySettings(context);

        //use zero index to get an initial point
        //for the context.
        double prevX = dots.get(0).getX();
        double prevY = dots.get(0).getY();
        context.moveTo(prevX, prevY);

        double canvasX;
        double canvasY;
        double controlX;
        double controlY;
        context.beginPath();
        for(Dot dot : dots) {
            canvasX = canvas.getWidth() - dot.getX();
            canvasY = canvas.getHeight() - dot.getY();
            controlX = (canvasX + prevX) / 2; // average of last point and new point
            controlY = prevY; // keep y coor the same. Creates an interesting curve effect.
            context.quadraticCurveTo(controlX, controlY, canvasX, canvasY);
            prevX = canvasX;
            prevY = canvasY;
            context.stroke();
        }
        context.closePath();
    }
    /**
     * Draws bezier curves between dots
     * @param canvas canvas to draw on
     */
    public void drawBezierCurve(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        applySettings(context);
        //use zero index to get an initial point
        //for the context.
        double prevX = dots.get(0).getX();
        double prevY = dots.get(0).getY();
        context.moveTo(prevX, prevY);

        double controlX1;
        double controlY1;
        double controlX2;
        double controlY2;
        double canvasX;
        double canvasY;
        context.beginPath();
        for(Dot dot : dots) {
            canvasX = canvas.getWidth() - dot.getX();
            canvasY = canvas.getHeight() - dot.getY();
            controlX1 = (canvasX + prevX) / 2; // average of last point and new point
            controlY1 = prevY; // keep y coor the same. Creates an interesting curve effect.
            controlY2 = (canvasY + prevY) / 2;
            controlX2 = prevX;
            context.bezierCurveTo(controlX1, controlY1, controlX2, controlY2, canvasX, canvasY);
            prevX = canvasX;
            prevY = canvasY;
            context.stroke();
        }
        context.closePath();
    }
    /**
     * Applies the user's settings (set under the options tab)
     * @param context the context to use (from a canvas)
     */
    public void applySettings(GraphicsContext context) {
        context.setStroke(color);
    }
    private double[] getCoordinatesFromLine(String string)
            throws InputMismatchException, NumberFormatException {
        String[] components = string.split(",");
        double[] xAndY = new double[2];
        xAndY[X_POS_IN_ARRAY] = Double.parseDouble(components[X_POS_IN_ARRAY]);
        xAndY[Y_POS_IN_ARRAY] = Double.parseDouble(components[Y_POS_IN_ARRAY]);
        return xAndY;
    }
}