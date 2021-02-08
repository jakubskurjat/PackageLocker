import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private Alert alert;

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
            alert.setContentText("Please complete all fields.");
            alert.show();
        } else {
            alert.setContentText("The user with the given e-mail \"" + inEmailStafferTxt.getText() + "\" does not exist or the wrong password was given.");
            alert.show();
        }
    }

    @FXML
    void initialize() {
        assert signInStafferButton != null : "fx:id=\"signInStafferButton\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
        assert inEmailStafferTxt != null : "fx:id=\"inEmailStafferTxt\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
        assert inPasswordStafferTxt != null : "fx:id=\"inPasswordStafferTxt\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";

        alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText("Error");
        dialogPane.getStylesheets().add(
                getClass().getResource("myDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

    }
}
