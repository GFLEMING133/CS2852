/**
 * CS2852
 *
 */
package flemingg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

/**
 * Dot2DotController
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
    Text status;

    private Picture pic;
    private BufferedWriter logger;

    /**
     * initializes the GUI
     * @param url location of fxml
     * @param resources any info needed to find url
     */
    public void initialize(URL url, ResourceBundle resources) {
        System.out.println("Called the initialize method.");
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
        lines.setOnAction(ae ->  {
            pic.drawLines(screenCanvas);
        });
        dots.setOnAction(ae -> {
            pic.drawDots(screenCanvas);
        });

        open.setOnAction(ae -> {
            openFile();
        });
    }

    /**
     * Event handler for opening a file
     * Presents the dot file chooser to the user.
     * @throws IOException if dots is invalid
     */
    public void openFile() {
        File dots = getDotFileChooser().showOpenDialog(null);
        if (dots!=null) {
            pic = new Picture(screenCanvas.getWidth(), screenCanvas.getHeight());
            try {
                pic.load(dots);
            } catch (IOException e) {
                status.setFill(Color.RED);
                status.setText("The selected file couldn't be read from or opened.");
                logError("The selected file resulted in an IOException.");
            } catch (InputMismatchException e) {
                status.setFill(Color.RED);
                status.setText("The file submitted had mis-formatted data.");
                logError("The file submitted by the user was not " +
                        "formatted correctly, \n" +
                        "and could not be parsed into points.");
            } catch (NumberFormatException e) {
                status.setFill(Color.RED);
                status.setText("The file submitted had bad or unreadable data.");
                logError("The file submitted by the user did not have readable data.");
            }
        }
        else {
            status.setText("No file specified!");
            logError("User did not specify a file upon exiting the filechooser");
        }
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
    private void clear (){
        screenCanvas.getGraphicsContext2D()
                .clearRect(0, 0, screenCanvas.getWidth(), screenCanvas.getHeight());
    }
    private void logError(String message) {
        try {
            Date date = new Date();
            logger.write("An exception occurred, see details below: \n");
            logger.write("TIME: " + date +"\n");
            logger.write(message);
            logger.write("\n");
            logger.flush();
        } catch (IOException e) {
            status.setText("There was an error while logging an exception!");
        }
    }
}
