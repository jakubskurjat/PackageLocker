import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LaunchWindowController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnSignInAsAClient"
    private Button btnSignInAsAClient; // Value injected by FXMLLoader

    @FXML // fx:id="btnSignInAsAStaffer"
    private Button btnSignInAsAStaffer; // Value injected by FXMLLoader

    @FXML
    void btnSignInAsAClientAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignInAsAClient.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Sign in as a client");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void btnSignInAsAStafferAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignInAsAStaffer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Sign in as a staffer");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnSignInAsAClient != null : "fx:id=\"btnSignInAsAClient\" was not injected: check your FXML file 'launchWindow.fxml'.";
        assert btnSignInAsAStaffer != null : "fx:id=\"btnSignInAsAStaffer\" was not injected: check your FXML file 'launchWindow.fxml'.";
    }
}

