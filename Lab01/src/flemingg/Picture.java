/*
 * SE1021
 */
package flemingg;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.isDigit;

/**
 * Class to draw a picture
 * @author flemingg
 * @version 1
 */
public class Picture {
    private static final int WIDTH = 1;
    private static final int HEIGHT = 1;
    private double x;
    private double y;
    private double centeredXTranslator=x-WIDTH/2.0;
    private double centeredYTranslator=y-HEIGHT/2.0;
    private ArrayList<ArrayList<Double>> points = new ArrayList<>();

    /**
     * Loads
     * @param file file to read points from
     * @throws IOException if file has error
     */
    public void load(File file) throws IOException {
        InputStream io = new FileInputStream(file);
        DataInputStream dataInputStream= new DataInputStream(io);

        int i = 0;
        ArrayList<Double> pairOfPoints = new ArrayList<>();
        while(dataInputStream.available() != 0) {
            x = dataInputStream.readDouble();
            dataInputStream.readChar(); // ','
            y = (dataInputStream.readDouble());
            pairOfPoints.add(centeredXTranslator);
            pairOfPoints.add(centeredYTranslator);
            points.add(pairOfPoints);
            dataInputStream.readChar(); //consume newline
        }
    }

    /**
     * Method to draw dots on the given canvas
     * @param canvas the canvas to draw on
     */
    public void drawDots(Canvas canvas) {
        for (ArrayList<Double> pairOfPoints : points) {
            GraphicsContext drawer = canvas.getGraphicsContext2D();
            drawer.fillOval(pairOfPoints.get(0), pairOfPoints.get(1),WIDTH,HEIGHT);
        }

    }

    /**
     * Connects the dots with lines
     * @param canvas the canvas to draw on
     */
    public void drawLines(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.beginPath();

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
