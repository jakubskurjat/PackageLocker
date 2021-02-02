import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.hibernate.Session;

import javax.swing.*;

public class ClientViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane senderOrReceiveView;

    @FXML
    private VBox sendViewBox;

    @FXML
    private TextField sendersNameTxt;

    @FXML
    private TextField sendersLastNameTxt;

    @FXML
    private TextField sendersPhoneNumberTxt;

    @FXML
    private TextField sendersEmailTxt;

    @FXML
    private TextField receiverNameTxt;

    @FXML
    private TextField receiverLastNameTxt;

    @FXML
    private TextField receiverPhoneNumberTxt;

    @FXML
    private TextField receiverEmailTxt;

    @FXML
    private MenuButton sizeOfPackage;

    @FXML
    private MenuItem smallSize;

    @FXML
    private MenuItem mediumSize;

    @FXML
    private MenuItem bigSize;

    @FXML
    private Label dollarLabel;

    @FXML
    private VBox receiveViewBox;

    @FXML
    private Text loggedAsView;

    @FXML
    private Button showSendViewButton;

    @FXML
    private Button showReceiveViewButton;

    @FXML
    void onSmallSizeClicked(ActionEvent event) {
        sizeOfPackage.setText(smallSize.getText());
    }

    @FXML
    void onMediumSizeClicked(ActionEvent event) {
        sizeOfPackage.setText(mediumSize.getText());
    }

    @FXML
    void onBigSizeClicked(ActionEvent event) {
        sizeOfPackage.setText(bigSize.getText());
    }

    @FXML
    void calculatePackagePrice(ActionEvent event) {
        if(sizeOfPackage.getText().isEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Select size of package.");
        }
        else {
            Session session = LaunchWindowController.getFactory().openSession();

            AtomicReference<Double> price = new AtomicReference<>(0d);

            session.doWork(connection -> {
                try (CallableStatement callableStatement = connection.prepareCall(
                        "{ ? = call calculatePrice(?) }")) {
                    callableStatement.registerOutParameter(1, Types.DECIMAL);
                    callableStatement.setString(2, sizeOfPackage.getText());
                    callableStatement.execute();
                    price.set(callableStatement.getDouble(1));
                }
            });

            dollarLabel.setText(price + " $");
        }
    }

    @FXML
    void onSendPackage(ActionEvent event) {

    }



    @FXML
    void initialize() {
        assert senderOrReceiveView != null : "fx:id=\"senderOrReceiveView\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sendViewBox != null : "fx:id=\"sendViewBox\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sendersNameTxt != null : "fx:id=\"sendersNameTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sendersLastNameTxt != null : "fx:id=\"sendersLastNameTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sendersPhoneNumberTxt != null : "fx:id=\"sendersPhoneNumberTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sendersEmailTxt != null : "fx:id=\"sendersEmailTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverNameTxt != null : "fx:id=\"receiverNameTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverLastNameTxt != null : "fx:id=\"receiverLastNameTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverPhoneNumberTxt != null : "fx:id=\"receiverPhoneNumberTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverEmailTxt != null : "fx:id=\"receiverEmailTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sizeOfPackage != null : "fx:id=\"sizeOfPackage\" was not injected: check your FXML file 'clientView.fxml'.";
        assert smallSize != null : "fx:id=\"smallSize\" was not injected: check your FXML file 'clientView.fxml'.";
        assert mediumSize != null : "fx:id=\"mediumSize\" was not injected: check your FXML file 'clientView.fxml'.";
        assert bigSize != null : "fx:id=\"bigSize\" was not injected: check your FXML file 'clientView.fxml'.";
        assert dollarLabel != null : "fx:id=\"dollarLabel\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiveViewBox != null : "fx:id=\"receiveViewBox\" was not injected: check your FXML file 'clientView.fxml'.";
        assert loggedAsView != null : "fx:id=\"loggedAsView\" was not injected: check your FXML file 'clientView.fxml'.";
        assert showSendViewButton != null : "fx:id=\"showSendViewButton\" was not injected: check your FXML file 'clientView.fxml'.";
        assert showReceiveViewButton != null : "fx:id=\"showReceiveViewButton\" was not injected: check your FXML file 'clientView.fxml'.";

        loggedAsView.setText("Signed in as: "+ UserService.getActiveClient().getName() + " " +
                UserService.getActiveClient().getLastName());
    }
}
