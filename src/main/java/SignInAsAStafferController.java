import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents controller for the sign in as a staffer window.
 */
public class SignInAsAStafferController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signInStafferButton;

    @FXML
    private TextField inEmailStafferTxt;

    @FXML
    private PasswordField inPasswordStafferTxt;

    /**
     * This private field represents <code>Alert</code>.
     */
    private Alert alert;

    /**
     * This method allows you to sign in as a staffer if the user provides the appropriate information.
     * If the user provides incorrect information or does not provide it at all, an appropriate alert pops up.
     *
     * @param event represents <code>ActionEvent</code>.
     * @throws IOException when FXMLLoader has a problem with load FXML File.
     */
    @FXML
    void onSignInStafferClicked(ActionEvent event) throws IOException {
        if (UserService.isStafferInDatabase(inEmailStafferTxt, inPasswordStafferTxt)) {
            UserService.setActiveStaffer(inEmailStafferTxt, inPasswordStafferTxt);
            Parent root = FXMLLoader.load(getClass().getResource("stafferView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Staffer view");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Stage stage1 = (Stage) signInStafferButton.getScene().getWindow();
            stage1.close();
        } else if (inEmailStafferTxt.getText().isEmpty() | inPasswordStafferTxt.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Please complete all fields.");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("The user with the given e-mail \"" + inEmailStafferTxt.getText() + "\" does not exist or the wrong password was given.");
            alert.show();
        }
    }

    /**
     * This method is called when creating the sign in as a client window.
     */
    @FXML
    void initialize() {
        assert signInStafferButton != null : "fx:id=\"signInStafferButton\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
        assert inEmailStafferTxt != null : "fx:id=\"inEmailStafferTxt\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
        assert inPasswordStafferTxt != null : "fx:id=\"inPasswordStafferTxt\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
    }
}
