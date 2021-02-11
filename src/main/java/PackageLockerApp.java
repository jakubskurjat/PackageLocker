import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class represents application.
 */
public class PackageLockerApp extends Application {

    /**
     * This overridden method is called when the application is started.
     * It shows us launch window.
     *
     * @param stage represents stage.
     * @throws Exception when FXMLLoader has a problem with load FXML File.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("launchWindow.fxml"));
        Scene scene = new Scene(root);
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Sign in option");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This is main application method.
     *
     * @param args represents arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}