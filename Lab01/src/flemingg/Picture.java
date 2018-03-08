/*
 * CS2852
 * Name: Grace FLeming
 * Date: 3/7/18
 * Assignment: Lab01
 */
package flemingg;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;

import static java.lang.Character.isDigit;

/**
 * Class to draw a picture
 * @author flemingg
 * @version 1
 */
public class Picture {
    private static final int DOT_WIDTH = 2;
    private static final int DOT_HEIGHT = 2;

    private double canvasHeight = 0;
    private double canvasWidth = 0;

    private double centeredXTranslator;
    private double centeredYTranslator;
    private ArrayList<Dot> dots = new ArrayList<>();

    /**
     * Requires the height and width of the given canvas item
     * @param height for height of canvas
     * @param width width of canvas
     */
    public Picture(double width, double height) {
        this.canvasHeight = height;
        this.canvasWidth = width;
    }
    /**
     * Loads
     * @param file file to read points from
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
        centeredYTranslator = canvasHeight -(y * canvasHeight + (DOT_HEIGHT / 2.0));
    }

    /**
     * Method to draw dots on the given canvas
     * @param canvas the canvas to draw on
     */
    public void drawDots(Canvas canvas) {
        clear(canvas);
        for (Dot dot : dots) {
            GraphicsContext drawer = canvas.getGraphicsContext2D();
            double canvasx = dot.getX();
            double canvasy = dot.getY();
            drawer.fillOval(canvasx, canvasy, DOT_WIDTH, DOT_HEIGHT);
            System.out.println("Drew pts: " + canvasx + "," + canvasy);
        }
    }
    /**
     * Connects the dots with lines
     * @param canvas the canvas to draw on
     */
    public void drawLines(Canvas canvas) {
        clear(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.moveTo(0, 0);
        context.beginPath();
        for(int i = 0; i < dots.size(); i++) {
            double canvasx = dots.get(i).getX();
            double canvasy = dots.get(i).getY();
            context.lineTo(canvasx, canvasy);
            context.stroke();
        }
        context.closePath();

    }
    private void clear (Canvas canvas){
        canvas.getGraphicsContext2D().clearRect(0, 0, canvasWidth, canvasHeight);
    }
}
