import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.swing.*;
import java.math.BigInteger;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

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
    private TextField receiverLockerAddressTxt;

    @FXML
    private TextField senderLockerAddressTxt;

    @FXML
    private TextField receiveNumberOfPackageTxt;

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
        if (sizeOfPackage.getText().isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Select size of package.");
        } else {
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
        if(senderLockerAddressTxt.getText().isEmpty() | receiverLockerAddressTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Please give an address.");
        }
        else if(receiverNameTxt.getText().isEmpty() | receiverLastNameTxt.getText().isEmpty() | receiverEmailTxt.getText().isEmpty()
        |receiverPhoneNumberTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"Please complete all fields.");
        }else {
            Session session = SessionFactoryCreator.getFactory().openSession();

            Client activeClient = UserService.getActiveClient();

            Query query = session.createQuery("FROM Client WHERE phoneNumber IN :phone");
            query.setParameter("phone", BigInteger.valueOf(Long.parseLong(receiverPhoneNumberTxt.getText())));
            Client receiverClient = (Client) query.getResultList().get(0);

            Query query1 = session.createQuery("SELECT id FROM PackageLockers WHERE addressLocker = '" + senderLockerAddressTxt.getText() + "'");
            Query query2 = session.createQuery("SELECT id FROM PackageLockers WHERE addressLocker = '" + receiverLockerAddressTxt.getText() + "'");

            session.doWork(connection -> {
                try (CallableStatement callableStatement = connection.prepareCall(
                        "{ call sendPackage(?,?,?,?,?,?) }")) {
                    callableStatement.setString(1, sizeOfPackage.getText());
                    callableStatement.setInt(2, activeClient.getId());
                    callableStatement.setInt(3, receiverClient.getId());
                    callableStatement.setInt(4, (Integer) query1.getResultList().get(0));
                    callableStatement.setInt(5, (Integer) query2.getResultList().get(0));
                    callableStatement.setInt(6, 211);
                    callableStatement.execute();
                }
            });
        }
    }

    @FXML
    void onReceivePackage(ActionEvent actionEvent) {
        Session session = SessionFactoryCreator.getFactory().openSession();
        Client activeClient = UserService.getActiveClient();

        System.out.println(Integer.parseInt(receiveNumberOfPackageTxt.getText()));
        System.out.println(activeClient.getId());

        session.doWork(connection -> {
            try (CallableStatement callableStatement = connection.prepareCall(
                    "{ call receivePackage(?,?) }")) {
                callableStatement.setString(1, receiveNumberOfPackageTxt.getText());
                callableStatement.setInt(2, activeClient.getId());
                callableStatement.execute();
            }
        });
    }

    @FXML
    void onShowSendPackages(MouseEvent mouseEvent) {

    }

    @FXML
    void onShowReceivedPackages(MouseEvent mouseEvent) {
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
        assert receiveNumberOfPackageTxt != null : "fx:id=\"receiveNumberOfPackageTxt\" was not injected: check your FXML file 'clientView.fxml'.";

        loggedAsView.setText("Signed in as: " + UserService.getActiveClient().getName() + " " +
                UserService.getActiveClient().getLastName());
    }
}
