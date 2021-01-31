import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private Text loggedAsView;

    @FXML
    void onShowReceiveView(ActionEvent event) {
        receiveViewBox.setVisible(true);
        sendViewBox.setVisible(false);
        showReceiveViewButton.setDisable(true);
        showSendViewButton.setDisable(false);
    }

    @FXML
    void onShowSendView(ActionEvent event) {
        sendViewBox.setVisible(true);
        receiveViewBox.setVisible(false);

        showReceiveViewButton.setDisable(false);
        showSendViewButton.setDisable(true);
    }

    public void calculatePackagePrice(ActionEvent actionEvent) {
    }

    public void onSendPackage(ActionEvent actionEvent) {
    }


    @FXML
    void initialize() {
        showSendViewButton.setDisable(true);
        loggedAsView.setText("Signed in as: "+ UserService.activeUser.getName() + " " +
                UserService.activeUser.getLastName());

    }
}
