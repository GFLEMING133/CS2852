/*
 * CS2852
 * Spring 2018
 * Lab 1 Dot2Dot
 * Name: Grace Fleming
 * Createdc 3/5/2018
 */
package flemingg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.net.URL;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

/**
 * Dot2DotController handles scene events & initializes
 * the scene.
 * @author flemingg
 * @version 1.0
 */
public class Dot2DotController implements Initializable {
    @FXML
    Canvas screenCanvas;
    @FXML
    MenuItem lines;
    @FXML
    MenuItem open;
    @FXML
    MenuItem dots;
    @FXML
    MenuItem quadratic;
    @FXML
    MenuItem bezier;
    @FXML
    ColorPicker color;
    @FXML
    TextField radius;
    @FXML
    Text status;

    private Picture pic;
    private BufferedWriter logger;
    private boolean loadSuccessful;

    /**
     * initializes the GUI & sets up logging.
     * @param url location of fxml
     * @param resources any info needed to find url
     */
    public void initialize(URL url, ResourceBundle resources) {
        System.out.println("Called the initialize method.");
        pic = new Picture(screenCanvas.getWidth(), screenCanvas.getHeight());
        try {
            FileWriter writer = new FileWriter("Dot2DotErrorLog.txt");
            logger = new BufferedWriter(writer);
            logger.write("Program started at time "
                    + System.currentTimeMillis() + "\n");
        } catch (FileNotFoundException e) {
            status.setText("Oh no! The log file wasn't found.");
        } catch (IOException e) {
            status.setText("Oh no! The log file wasn't loaded correctly.");
        }
        lines.setOnAction(ae -> {
            if (loadSuccessful) {
                clear(screenCanvas);
                pic.drawLines(screenCanvas);
            }
        });
        dots.setOnAction(ae -> {
            if (loadSuccessful) {
                clear(screenCanvas);
                pic.drawDots(screenCanvas);
            }
        });
        quadratic.setOnAction(ae -> {
            if (loadSuccessful) {
                clear(screenCanvas);
                pic.drawQuadraticCurve(screenCanvas);
            }
        });
        bezier.setOnAction(ae -> {
            if (loadSuccessful) {
                clear(screenCanvas);
                pic.drawBezierCurve(screenCanvas);
            }
        });
        radius.setOnAction(ae -> {
            try {
                pic.setDotRadius(Integer.parseInt(radius.getText()));
            } catch (NumberFormatException e) {
                status.setText("Enter an integer please.");
            }

        });
        color.setOnAction(ae -> {
            if (color.getValue() != null) {
                try {
                    pic.setColor(color.getValue());
                    clear(screenCanvas);
                    pic.drawDots(screenCanvas);
                    pic.drawLines(screenCanvas);
                } catch (NullPointerException e) {
                    status.setText("oops! We had a problem setting that color.");
                }
            }
        });
        open.setOnAction(ae -> {
            openFile();
        });
    }
    /**
     * Event handler for opening a file
     * Presents the dot file chooser to the user.
     */
    public void openFile() {
        File dots = getDotFileChooser().showOpenDialog(null);
        if (dots != null) {
            loadSuccessful = true;
            clear(screenCanvas);
            try {
                pic.load(dots);
                status.setFill(Color.BLUE);
                status.setText("Points were loaded successfully!");
            } catch (IOException e) {
                status.setFill(Color.RED);
                status.setText("The selected file couldn't be read from or opened.");
                loadSuccessful = false;
                logError("The selected file resulted in an IOException.");
            } catch(InputMismatchException e) {
                loadSuccessful = false;
                status.setFill(Color.RED);
                status.setText("The file submitted had mis-formatted data.");
                logError("The file submitted by the user was not " +
                        "formatted correctly, \n" +
                        "and could not be parsed into points.");
            } catch(NumberFormatException e) {
                loadSuccessful = false;
                status.setFill(Color.RED);
                status.setText("The file submitted had bad data," +
                        " or was not the right type of file.");
                logError("The file submitted by the user did not have readable data.");
            }
        } else {
            status.setText("No file specified!");
            logError("User did not specify a file upon exiting the file chooser");
        }
        pic.drawDots(screenCanvas);
        pic.drawLines(screenCanvas);
    }
    /**
     * Event handler for closing the screen.
     * Uses Platform.exit()
     */
    public void close() {
        Platform.exit();
    }

    private FileChooser getDotFileChooser(){
        //Set extension filter
        FileChooser.ExtensionFilter extFilterTxt =
                new FileChooser.ExtensionFilter("dot files (*.dot)", "*.dot");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(extFilterTxt);
        return chooser;
    }
    private void logError(String message) {
        try {
            Date date = new Date();
            logger.write("An exception occurred, see details below: \n");
            logger.write("TIME: " + date + "\n");
            logger.write(message);
            logger.write("\n");
            logger.flush();
        } catch (IOException e) {
            status.setText("There was an error while logging an exception!");
        }
    }
    private void clear(Canvas canvas){
        canvas.getGraphicsContext2D().clearRect(0, 0,
                canvas.getWidth(), canvas.getHeight());
    }
}
