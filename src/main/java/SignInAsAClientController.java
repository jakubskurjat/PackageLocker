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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        if(UserService.isClientInDatabase(inEmailClientTxt,inPasswordClientTxt)){
            UserService.setActiveClient(inEmailClientTxt, inPasswordClientTxt);
            Parent root = FXMLLoader.load(getClass().getResource("clientView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Client view");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if(inEmailClientTxt.getText().isEmpty() | inPasswordClientTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Please complete all fields.");
        }
        else{
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"The user with the given e-mail \"" + inEmailClientTxt.getText() + "\" does not exist or the wrong password was given.");
        }
    }

    @FXML
    void onSignUpClientClicked(ActionEvent event) {
        if(UserService.isClientInDatabase(upEmailClientTxt,upPasswordClientTxt)){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"The user with the given e-mail \"" + inEmailClientTxt.getText() + "\" already exist.");
        }
        else if(upNameClientTxt.getText().isEmpty() | upLastNameTxt.getText().isEmpty() |
                upEmailClientTxt.getText().isEmpty() | upPhoneNumberClientTxt.getText().isEmpty() |
                upPasswordClientTxt.getText().isEmpty() | upConfirmPasswordClientTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Please complete all fields.");
        }
        else if(!upPasswordClientTxt.getText().equals(upConfirmPasswordClientTxt.getText())){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"The password and confirm password fields are not the same.");
        }
        else{
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Registered user. You can register now.");
            UserService.addClient(upNameClientTxt,upLastNameTxt,upEmailClientTxt,upPhoneNumberClientTxt,upPasswordClientTxt);
            System.out.println();
        }
    }

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
