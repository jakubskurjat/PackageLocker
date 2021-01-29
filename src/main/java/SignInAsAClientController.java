import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInAsAClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInClientButton;

    @FXML
    private TextField inEmailClientTxt;

    @FXML
    private PasswordField inPasswordClientTxt;

    @FXML
    private Button signUpClientButton;

    @FXML
    private TextField upNameClientTxt;

    @FXML
    private TextField upLastNameTxt;

    @FXML
    private TextField upEmailClientTxt;

    @FXML
    private TextField upPhoneNumberClientTxt;

    @FXML
    private PasswordField upPasswordClientTxt;

    @FXML
    private PasswordField upConfirmPasswordClientTxt;

    @FXML
    void onSignInClientClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ClientView2.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("ClientView2.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Client view");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void onSignUpClientClicked(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert signInClientButton != null : "fx:id=\"signInClientButton\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert inEmailClientTxt != null : "fx:id=\"inEmailClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert inPasswordClientTxt != null : "fx:id=\"inPasswordClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert signUpClientButton != null : "fx:id=\"signUpClientButton\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert upNameClientTxt != null : "fx:id=\"upNameClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert upLastNameTxt != null : "fx:id=\"upLastNameTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert upEmailClientTxt != null : "fx:id=\"upEmailClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert upPhoneNumberClientTxt != null : "fx:id=\"upPhoneNumberClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert upPasswordClientTxt != null : "fx:id=\"upPasswordClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";
        assert upConfirmPasswordClientTxt != null : "fx:id=\"upConfirmPasswordClientTxt\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";

    }
}
