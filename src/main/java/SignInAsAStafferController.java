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

import javax.swing.*;

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

    @FXML
    void onSignInStafferClicked(ActionEvent event) throws IOException {
        if(UserService.isStafferInDatabase(inEmailStafferTxt,inPasswordStafferTxt)){
            UserService.setActiveStaffer(inEmailStafferTxt, inPasswordStafferTxt);
            Parent root = FXMLLoader.load(getClass().getResource("stafferView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Staffer view");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if(inEmailStafferTxt.getText().isEmpty() | inPasswordStafferTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Please complete all fields.");
        }
        else{
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"The user with the given e-mail \"" + inEmailStafferTxt.getText() + "\" does not exist or the wrong password was given.");
        }
    }

    @FXML
    void initialize() {
        assert signInStafferButton != null : "fx:id=\"signInStafferButton\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
        assert inEmailStafferTxt != null : "fx:id=\"inEmailStafferTxt\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";
        assert inPasswordStafferTxt != null : "fx:id=\"inPasswordStafferTxt\" was not injected: check your FXML file 'signInAsAStaffer.fxml'.";

    }
}
