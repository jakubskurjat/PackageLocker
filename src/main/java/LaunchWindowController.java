import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents controller for the launch window.
 */
public class LaunchWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSignInAsAClient;

    @FXML
    private Button btnSignInAsAStaffer;

    /**
     * This private static field represents <code>SessionFactory</code>.
     */
    private static SessionFactory factory = SessionFactoryCreator.getFactory();

    /**
     * This method allows you to sign in as a client.
     *
     * @param event represents <code>ActionEvent</code>.
     * @throws IOException when FXMLLoader has a problem with load FXML File.
     */
    @FXML
    void btnSignInAsAClientAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signInAsAClient.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Sign in as a client");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This method allows you to sign in as a staffer.
     *
     * @param event represents <code>ActionEvent</code>.
     * @throws IOException when FXMLLoader has a problem with load FXML File.
     */
    @FXML
    void btnSignInAsAStafferAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signInAsAStaffer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Sign in as a staffer");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This method is called when creating the launch window view.
     */
    @FXML
    void initialize() {
        assert btnSignInAsAClient != null : "fx:id=\"btnSignInAsAClient\" was not injected: check your FXML file 'launchWindow.fxml'.";
        assert btnSignInAsAStaffer != null : "fx:id=\"btnSignInAsAStaffer\" was not injected: check your FXML file 'launchWindow.fxml'.";
    }

    /**
     * This is <code>factory</code> getter.
     *
     * @return <code>factory</code>.
     */
    public static SessionFactory getFactory() {
        return factory;
    }
}