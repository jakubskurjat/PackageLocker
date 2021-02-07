import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PackageLockerApp extends Application {

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

    public static void main(String[] args) {
        launch();
    }
}