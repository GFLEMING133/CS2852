/**
 * SE1021
 *
 */
package flemingg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private Picture pic;

    /**
     * initializes the GUI
     * @param url location of fxml
     * @param resources any info needed to find url
     */
    public void initialize(URL url, ResourceBundle resources) {
        System.out.println("Called the initialize method.");
        lines.setOnAction(ae -> pic.drawDots(screenCanvas));
        dots.setOnAction(ae -> pic.drawLines(screenCanvas));

        open.setOnAction(ae -> {
            try {
                openFile();
            } catch (IOException e) {
                System.out.println("Oh no! IOException in dots occurs.");
            }
        });

    }

    /**
     * Event handler for opening a file
     * Presents the dot file chooser to the user.
     * @throws IOException if dots is invalid
     */
    public void openFile() throws IOException {
        File dots = getDotFileChooser().showOpenDialog(null);
        Picture pic = new Picture(screenCanvas.getWidth(),screenCanvas.getHeight());
        pic.load(dots);
    }
    /**
     * Event handler for closing the screen.
     * Uses Platform.exit()
     */
    public void close() {
        Platform.exit();
    }

    /**
     * Loads the dots from a file
     * @param file file to load dots from
     */
    public void load(File file) {
    }
    private FileChooser getDotFileChooser(){
        //Set extension filter
        FileChooser.ExtensionFilter extFilterTxt =
                new FileChooser.ExtensionFilter("text files (*.dot)", "*.dot");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(extFilterTxt);
        return chooser;
    }
}
