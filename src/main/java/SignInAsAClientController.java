import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SignInAsAClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceBoxSendPane;




    @FXML
    void initialize() {
        assert choiceBoxSendPane != null : "fx:id=\"choiceBoxSendPane\" was not injected: check your FXML file 'SignInAsAClient.fxml'.";

        choiceBoxSendPane.setItems(FXCollections.observableArrayList(
                "Small","Medium","Big")
        );
        choiceBoxSendPane.setValue("Small");

    }
}