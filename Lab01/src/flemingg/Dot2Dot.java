/*
 * CS2852
 */
package flemingg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class that controls the main screen.
 * @author flemingg
 * @version 1.0
 */
public class Dot2Dot extends Application {
    /**
     * Class that starts the primaryStage
     * @param primaryStage main stage
     */
    public void start(Stage primaryStage) {
        Pane primaryRoot;
        String fxmlLocation = "Dot2DotController.fxml";
        try {
            primaryRoot = FXMLLoader.load(getClass()
                    .getResource(fxmlLocation));
        } catch (IOException |NullPointerException e) {
            primaryRoot = new Pane();
            primaryRoot.getChildren().add(
                    new Label("Could not load layout. " +
                        "Configuration error!\nMissing file: " + fxmlLocation));
        }
        primaryStage.setTitle("Dot2Dot");
        Scene primaryScene = new Scene(primaryRoot);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }
}
