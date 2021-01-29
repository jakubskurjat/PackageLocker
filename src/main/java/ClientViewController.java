import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ClientViewController {


    @FXML
    private Button showSendViewButton;

    @FXML
    private Button showReceiveViewButton;

    @FXML
    private AnchorPane senderOrReceiveView;

    @FXML
    private VBox sendViewBox;

    @FXML
    private ChoiceBox<?> choiceBoxSendPane;

    @FXML
    private VBox receiveViewBox;

    @FXML
    void onShowReceiveView(ActionEvent event) {
        receiveViewBox.setVisible(true);
        sendViewBox.setVisible(false);
    }

    @FXML
    void onShowSendView(ActionEvent event) {
        sendViewBox.setVisible(true);
        receiveViewBox.setVisible(false);
    }

    public void calculatePackagePrice(ActionEvent actionEvent) {
    }

    public void onSendPackage(ActionEvent actionEvent) {
    }
}
