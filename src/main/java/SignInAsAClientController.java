import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents controller for the sign in as a client window.
 */
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

    /**
     * This private field represents <code>Alert</code>.
     */
    private Alert alert;

    /**
     * This method allows you to sign in as a client if the user provides the appropriate information.
     * However, if the user forgets the password, he has the option to change the password.
     * If the user provides incorrect information or does not provide it at all, an appropriate alert pops up.
     *
     * @param event represents <code>ActionEvent</code>.
     * @throws IOException when FXMLLoader has a problem with load FXML File.
     */
    @FXML
    void onSignInClientClicked(ActionEvent event) throws IOException {
        if (UserService.isClientInDatabase(inEmailClientTxt, inPasswordClientTxt)) {
            UserService.setActiveClient(inEmailClientTxt, inPasswordClientTxt);
            Parent root = FXMLLoader.load(getClass().getResource("clientView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Client view");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Stage stage1 = (Stage) signInClientButton.getScene().getWindow();
            stage1.close();
        } else if (inEmailClientTxt.getText().isEmpty() | inPasswordClientTxt.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Please complete all fields.");
            alert.show();
        } else {
            UserService.changeYourPassword(inEmailClientTxt, inPasswordClientTxt);
        }
    }

    /**
     * This method allows you to sign up as a client if the user provides the appropriate information,
     * an appropriate alert pops up.
     * If the user provides incorrect information or does not provide it at all, an appropriate alert is pops up.
     *
     * @param event represents <code>ActionEvent</code>.
     * @throws IOException when FXMLLoader has a problem with load FXML File.
     */
    @FXML
    void onSignUpClientClicked(ActionEvent event) {
        if (UserService.isClientInDatabase(upEmailClientTxt, upPasswordClientTxt)) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("The user with the given e-mail \"" + inEmailClientTxt.getText() + "\" already exist.");
            alert.show();
        } else if (upNameClientTxt.getText().isEmpty() | upLastNameTxt.getText().isEmpty() |
                upEmailClientTxt.getText().isEmpty() | upPhoneNumberClientTxt.getText().isEmpty() |
                upPasswordClientTxt.getText().isEmpty() | upConfirmPasswordClientTxt.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Please complete all fields.");
            alert.show();
        } else if (!upPasswordClientTxt.getText().equals(upConfirmPasswordClientTxt.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("The password and confirm password fields are not the same.");
            alert.show();
        } else if (upPhoneNumberClientTxt.getText().length() != 9) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Phone number must be 9 numbers.");
            alert.show();
        } else if (!UserService.isNumber(upPhoneNumberClientTxt)) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Phone number is not a number.");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            UserService.preparingDialogPane("INFORMATION", alert);
            alert.setContentText("Registered user. You can sign in now.");
            alert.show();
            UserService.addClient(upNameClientTxt, upLastNameTxt, upEmailClientTxt, upPhoneNumberClientTxt, upPasswordClientTxt, upConfirmPasswordClientTxt);
        }
    }

    /**
     * This method is called when creating the sign in as a client window.
     */
    @FXML
    void initialize() {
        assert signInClientButton != null : "fx:id=\"signInClientButton\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert inEmailClientTxt != null : "fx:id=\"inEmailClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert inPasswordClientTxt != null : "fx:id=\"inPasswordClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert signUpClientButton != null : "fx:id=\"signUpClientButton\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert upNameClientTxt != null : "fx:id=\"upNameClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert upLastNameTxt != null : "fx:id=\"upLastNameTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert upEmailClientTxt != null : "fx:id=\"upEmailClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert upPhoneNumberClientTxt != null : "fx:id=\"upPhoneNumberClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert upPasswordClientTxt != null : "fx:id=\"upPasswordClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
        assert upConfirmPasswordClientTxt != null : "fx:id=\"upConfirmPasswordClientTxt\" was not injected: check your FXML file 'signInAsAClient.fxml'.";
    }
}
