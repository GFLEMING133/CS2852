/*
 * SE1021
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
    private static final int DOT_WIDTH = 1;
    private static final int DOT_HEIGHT = 1;

    private static double canvasHeight=0;
    private static double canvasWidth=0;

    private double x;
    private double y;
    private double centeredXTranslator;
    private double centeredYTranslator;
    private ArrayList<ArrayList<Double>> points = new ArrayList<>();
    /**
     * Requires the height and width of the given canvas item
     */
    public Picture(double width, double height) {
        this.canvasHeight=height;
        this.canvasWidth=width;
    }
    /**
     * Loads
     * @param file file to read points from
     * @throws IOException if file has error
     */
    public void load(File file) throws IOException {
        FileReader io = new FileReader(file);
        BufferedReader reader = new BufferedReader(io);

        int i = 0;
        String currentLine="";
        while(reader.ready()) {
            ArrayList<Double> pairOfPoints = new ArrayList<>();
            currentLine=reader.readLine();
            getCoordinatesFromLine(currentLine);
            pairOfPoints.add(0,centeredXTranslator);
            pairOfPoints.add(1,centeredYTranslator);
            points.add(pairOfPoints);
        }
    }
    private void getCoordinatesFromLine(String string) throws InputMismatchException, IndexOutOfBoundsException{
        String[] components = string.split(",");
        System.out.println(components[0] + "," + components[1]);
        x = Double.parseDouble(components[0]);
        y = Double.parseDouble(components[1]);
        centeredXTranslator = x*canvasWidth-(DOT_WIDTH/2.0);
        centeredYTranslator =y*canvasHeight-(DOT_HEIGHT/2.0);

    }

    /**
     * Method to draw dots on the given canvas
     * @param canvas the canvas to draw on
     */
    public void drawDots(Canvas canvas) {
        for (ArrayList<Double> pairOfPoints : points) {
            GraphicsContext drawer = canvas.getGraphicsContext2D();
            double canvasx = pairOfPoints.get(0);
            double canvasy = pairOfPoints.get(1);
            drawer.fillOval(canvasx,canvasy,DOT_WIDTH,DOT_HEIGHT);
            System.out.println("Drew pts: " + canvasx + "," + canvasy);
        }

    }

    /**
     * Connects the dots with lines
     * @param canvas the canvas to draw on
     */
    public void drawLines(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.moveTo(0,0);
        context.beginPath();
        for(int i = 0; i < points.size(); i++) {
            ArrayList<Double> pairOfPoints = points.get(i);
            double canvasx = pairOfPoints.get(0);
            double canvasy = pairOfPoints.get(1);
            context.lineTo(canvasx,canvasy);
            context.stroke();
        }
        context.closePath();

    }
    private static double pullOutDouble(String string) {
        String temp= "";
        for(int i = 0; i<string.length(); i++) {
            if (isDigit(string.charAt(i)) || string.charAt(i)=='.') {
                temp= temp + string.charAt(i);
            }
        }
        return Double.parseDouble(temp);
    }
}
