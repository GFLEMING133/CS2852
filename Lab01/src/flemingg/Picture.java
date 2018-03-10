/*
 * CS2852
 * Spring 2018
 * Lab 1 Dot2Dot
 * Name: Grace Fleming
 * Createdc 3/5/2018
 */
package flemingg;

import com.sun.corba.se.impl.orbutil.graph.Graph;
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
    private static int DOT_WIDTH = 2;
    private static int DOT_HEIGHT = 2;

    private double canvasHeight = 0;
    private double canvasWidth = 0;

    private double centeredXTranslator;
    private double centeredYTranslator;
    private ArrayList<Dot> dots = new ArrayList<>();

    public void setWidth(int width) {
        DOT_WIDTH = width;
        DOT_HEIGHT = width;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

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
     * Loads
     * @param file file to read points from
     * @throws InputMismatchException if the file contains
     *         data seperated by commas but in an incorrect format
     *         (e.g., not in double form)
     * @throws IOException if file has error
     */
    public void load(File file)
            throws IOException, InputMismatchException{
        FileReader io = new FileReader(file);
        BufferedReader reader = new BufferedReader(io);
        String currentLine = "";
        while(reader.ready()) {
            currentLine = reader.readLine();
            getCoordinatesFromLine(currentLine);
            dots.add(new Dot(centeredXTranslator, centeredYTranslator));
        }
    }
    private void getCoordinatesFromLine(String string)
            throws InputMismatchException, NumberFormatException {
        String[] components = string.split(",");
        double x = Double.parseDouble(components[0]);
        double y = Double.parseDouble(components[1]);
        centeredXTranslator = canvasWidth - (x * canvasWidth + (DOT_WIDTH / 2.0));
        centeredYTranslator = canvasHeight - (y * canvasHeight + (DOT_HEIGHT / 2.0));
    }

    /**
     * Draws every dot in the list of dots on the canvas.
     * @param canvas the canvas to draw on
     */
    public void drawDots(Canvas canvas) {
        clear(canvas);
        GraphicsContext drawer = canvas.getGraphicsContext2D();
        applySettings(drawer);
        drawer.setStroke(color);
        for (Dot dot : dots) {
            double canvasx = dot.getX();
            double canvasy = dot.getY();
            drawer.fillOval(canvasx, canvasy, DOT_WIDTH, DOT_HEIGHT);
        }
    }
    /**
     * Connects the dots on the canvas with liens
     * @param canvas the canvas to draw on
     */
    public void drawLines(Canvas canvas) {

        GraphicsContext context = canvas.getGraphicsContext2D();
        applySettings(context);
        context.moveTo(0, 0);
        context.beginPath();
        for(Dot dot : dots) {
            double canvasx = dot.getX();
            double canvasy = dot.getY();
            context.lineTo(canvasx, canvasy);
            context.stroke();
        }
        context.closePath();
    }

    /**
     * Connects the dots listed in the dots list
     * with quadratic curves.
     * @param canvas the canvas to draw on
     *
     */
    public void drawQuadraticCurve(Canvas canvas) {
        clear(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();
        applySettings(context);
        double prevX = dots.get(0).getX();
        double prevY  =  dots.get(0).getY();
        context.moveTo(prevX, prevY);
        double controlX ;
        double controlY;
        context.beginPath();
        for(Dot dot : dots) {
            double canvasx = dot.getX();
            double canvasy = dot.getY();
            controlX = (canvasx + prevX) / 2; // average of last point and new point
            controlY = prevY; // keep y coor the same. Creates an interesting curve effect.
            context.quadraticCurveTo(controlX, controlY, canvasx, canvasy);
            prevX = canvasx;
            prevY = canvasy;

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
        clear(canvas);
        double prevX = dots.get(0).getX();
        double prevY  =  dots.get(0).getY();
        context.moveTo(prevX, prevY);
        double controlX1;
        double controlY1;
        double controlX2;
        double controlY2;
        context.beginPath();
        for(Dot dot : dots) {
            double canvasx = dot.getX();
            double canvasy = dot.getY();
            controlX1 = (canvasx + prevX) / 2; // average of last point and new point
            controlY1 = prevY; // keep y coor the same. Creates an interesting curve effect.
            controlY2 = (canvasy + prevY) / 2;
            controlX2 = prevX;
            context.bezierCurveTo(controlX1, controlY1, controlX2, controlY2, canvasx, canvasy);
            prevX = canvasx;
            prevY = canvasy;
            context.stroke();
        }
        context.closePath();
    }
    private void clear(Canvas canvas){
        canvas.getGraphicsContext2D().clearRect(0, 0, canvasWidth, canvasHeight);
    }
    public void applySettings (GraphicsContext context) {
        context.setStroke(color);
    }
}
