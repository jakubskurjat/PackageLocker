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
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Optional;
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
    void onCalculatePackagePriceClicked(ActionEvent event) {
        if (sizeOfPackage.getText().isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please select size of the package.");
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
    void onSendPackageClicked(ActionEvent event) {
        Session session = SessionFactoryCreator.getFactory().openSession();
        Client activeClient = UserService.getActiveClient();

        String query = "FROM Client WHERE name = '" + receiverNameTxt.getText() +
                "' AND lastName = '" + receiverLastNameTxt.getText() + "' AND email = '" + receiverEmailTxt.getText() +
                "' AND phoneNumber = '" + receiverPhoneNumberTxt.getText() + "'";

        Optional<Client> clientFromDB = session.createQuery(query).uniqueResultOptional();

        String querySender = "FROM PackageLockers WHERE addressLocker = '" + senderLockerAddressTxt.getText() + "'";
        String queryReceiver = "FROM PackageLockers WHERE addressLocker = '" + receiverLockerAddressTxt.getText() + "'";

        Optional<PackageLockers> senderPackageLocker = session.createQuery(querySender).uniqueResultOptional();
        Optional<PackageLockers> receiverPackageLocker = session.createQuery(queryReceiver).uniqueResultOptional();

        if (clientFromDB.isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "The client with the given data does not exist.");
        } else if (senderPackageLocker.isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "There is no sender's package locker with the given address.");
        } else if (receiverPackageLocker.isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "There is no receiver's package locker with the given address.");
        } else if (sizeOfPackage.getText().isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please select size of the package.");
        } else if (senderPackageLocker.get().getAddressLocker().equals(receiverPackageLocker.get().getAddressLocker())) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Package locker addresses have to be different.");
        } else if (activeClient.equals(clientFromDB)) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Unable to send the package to yourself.");
        } else {
            Query query1 = session.createQuery("SELECT id FROM PackageLockers WHERE addressLocker = '" + receiverPackageLocker.get().getAddressLocker() + "'");

            session.doWork(connection -> {
                try (CallableStatement callableStatement = connection.prepareCall(
                        "{ call sendPackage(?,?,?,?,?) }")) {
                    callableStatement.setString(1, sizeOfPackage.getText());
                    callableStatement.setInt(2, activeClient.getId());
                    callableStatement.setInt(3, clientFromDB.get().getId());
                    callableStatement.setInt(4, senderPackageLocker.get().getId());
                    callableStatement.setInt(5, (Integer) query1.getResultList().get(0));
                    callableStatement.execute();
                }
            });
        }
    }

    @FXML
    void onReceivePackageClicked(ActionEvent actionEvent) {
        Session session = SessionFactoryCreator.getFactory().openSession();
        Client activeClient = UserService.getActiveClient();

//        String queryPackage = "FROM Package WHERE id = " + receiveNumberOfPackageTxt.getText();

//        Optional<Package> receivePackage = session.createQuery(queryPackage).uniqueResultOptional();

//        if(receivePackage.isEmpty()){
//            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),"You do not have a package with this number to receive.");
//        } else {

            Query query = session.createQuery("SELECT id FROM Client WHERE name = '" + activeClient.getName() + "'");

            /*
                O co z tym kurwa chodzi??????
                Ten sam błąd jest w metodzie onSendpackage.
                Chujowo to jest napisane, ale działa.
                Zabezpieczenia nie działają, poniważ są źle zrobione mapowania relacji dla klasy Package.
             */

//            System.out.println(activeClient.getId());
//            System.out.println((Integer) query.getResultList().get(0));
//            System.out.println(activeClient.getId() == (Integer) query.getResultList().get(0));

            session.doWork(connection -> {
                try (CallableStatement callableStatement = connection.prepareCall(
                        "{ call receivePackage(?,?) }")) {
                    callableStatement.setInt(1, Integer.parseInt(receiveNumberOfPackageTxt.getText()));
                    callableStatement.setInt(2, (Integer) query.getResultList().get(0));
                    callableStatement.execute();
                }
            });
//        }
    }

    @FXML
    void onShowSendPackagesClicked(MouseEvent mouseEvent) {
    }

    @FXML
    void onShowReceivedPackagesClicked(MouseEvent mouseEvent) {
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
