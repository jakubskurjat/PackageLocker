import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.swing.*;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ClientViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text loggedAsView;

    @FXML
    private Button signOutClientButton;

    @FXML
    private VBox sendViewBox;

    @FXML
    private TextField senderLockerAddressTxt;

    @FXML
    private TextField receiverNameTxt;

    @FXML
    private TextField receiverLastNameTxt;

    @FXML
    private TextField receiverPhoneNumberTxt;

    @FXML
    private TextField receiverEmailTxt;

    @FXML
    private TextField receiverLockerAddressTxt;

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
    private TextField receiveNumberOfPackageTxt;

    @FXML
    private TableView<PackagesView> packagesShippedTable;

    @FXML
    private TableColumn<?, ?> idColS;

    @FXML
    private TableColumn<?, ?> sizeColS;

    @FXML
    private TableColumn<?, ?> shipmentDateColS;

    @FXML
    private TableColumn<?, ?> collectionDateColS;

    @FXML
    private TableColumn<?, ?> priceColS;

    @FXML
    private TableColumn<?, ?> senderColS;

    @FXML
    private TableColumn<?, ?> receiverColS;

    @FXML
    private TableColumn<?, ?> senderLockerColS;

    @FXML
    private TableColumn<?, ?> receiverLockerColS;

    @FXML
    private TableView<PackagesView> packagesReceivedTable;

    @FXML
    private TableColumn<?, ?> idColR;

    @FXML
    private TableColumn<?, ?> sizeColR;

    @FXML
    private TableColumn<?, ?> shipmentDateColR;

    @FXML
    private TableColumn<?, ?> collectionDateColR;

    @FXML
    private TableColumn<?, ?> priceColR;

    @FXML
    private TableColumn<?, ?> senderColR;

    @FXML
    private TableColumn<?, ?> receiverColR;

    @FXML
    private TableColumn<?, ?> senderLockerColR;

    @FXML
    private TableColumn<?, ?> receiverLockerColR;

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
        } else if (activeClient.getId() == clientFromDB.get().getId()) {
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

        String queryPackage = "FROM PackagesView WHERE id = '" + receiveNumberOfPackageTxt.getText()
                + "' AND receiver = '" + activeClient.getName() + " " + activeClient.getLastName() + "'";

        Optional<Shipment> receivePackage = session.createQuery(queryPackage).uniqueResultOptional();

        if (receivePackage.isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "You do not have a package with this number to receive.");
        } else {

            Query query = session.createQuery("SELECT id FROM Client WHERE name = '" + activeClient.getName() + "'");

            session.doWork(connection -> {
                try (CallableStatement callableStatement = connection.prepareCall(
                        "{ call receivePackage(?,?) }")) {
                    callableStatement.setInt(1, Integer.parseInt(receiveNumberOfPackageTxt.getText()));
                    callableStatement.setInt(2, (Integer) query.getResultList().get(0));
                    callableStatement.execute();
                }
            });
        }
    }

    @FXML
    void onShowSendPackagesClicked(MouseEvent mouseEvent) {
        Client activeClient = UserService.getActiveClient();

        String queryReceivedView = "SELECT * FROM packagesview WHERE sender = '" +
                activeClient.getName() + " " + activeClient.getLastName() + "'";

        preparingTableView(queryReceivedView, packagesShippedTable, idColS, sizeColS, shipmentDateColS, collectionDateColS, priceColS, senderColS, receiverColS, senderLockerColS, receiverLockerColS);
    }

    private void preparingTableView(String queryReceivedView, TableView<PackagesView> packagesTable, TableColumn<?, ?> idCol, TableColumn<?, ?> sizeCol, TableColumn<?, ?> shipmentDateCol, TableColumn<?, ?> collectionDateCol, TableColumn<?, ?> priceCol, TableColumn<?, ?> senderCol, TableColumn<?, ?> receiverCol, TableColumn<?, ?> senderLockerCol, TableColumn<?, ?> receiverLockerCol) {
        Session session = SessionFactoryCreator.getFactory().openSession();

        EntityManagerFactory emf = session.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Query query = em.createNativeQuery(queryReceivedView, PackagesView.class);

        List<PackagesView> list = query.getResultList();

        ObservableList<PackagesView> packagesList = FXCollections.observableArrayList(list);

        packagesTable.setItems(packagesList);


        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        sizeCol.setCellValueFactory(
                new PropertyValueFactory<>("size")
        );
        shipmentDateCol.setCellValueFactory(
                new PropertyValueFactory<>("shipmentDate")
        );
        collectionDateCol.setCellValueFactory(
                new PropertyValueFactory<>("collectionDate")
        );
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        senderCol.setCellValueFactory(
                new PropertyValueFactory<>("sender")
        );
        receiverCol.setCellValueFactory(
                new PropertyValueFactory<>("receiver")
        );
        senderLockerCol.setCellValueFactory(
                new PropertyValueFactory<>("packageLockerAddressSender")
        );
        receiverLockerCol.setCellValueFactory(
                new PropertyValueFactory<>("packageLockerAddressReceiver")
        );
    }

    @FXML
    void onShowReceivedPackagesClicked(MouseEvent mouseEvent) {
        Client activeClient = UserService.getActiveClient();

        String queryReceivedView = "SELECT * FROM packagesview WHERE receiver = '" +
                activeClient.getName() + " " + activeClient.getLastName() + "'";

        preparingTableView(queryReceivedView, packagesReceivedTable, idColR, sizeColR, shipmentDateColR, collectionDateColR, priceColR, senderColR, receiverColR, senderLockerColR, receiverLockerColR);
    }

    public void signOutClick(ActionEvent actionEvent) {
        Stage stage1 = (Stage) signOutClientButton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void initialize() {
        assert loggedAsView != null : "fx:id=\"loggedAsView\" was not injected: check your FXML file 'clientView.fxml'.";
        assert signOutClientButton != null : "fx:id=\"signOutClientButton\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sendViewBox != null : "fx:id=\"sendViewBox\" was not injected: check your FXML file 'clientView.fxml'.";
        assert senderLockerAddressTxt != null : "fx:id=\"senderLockerAddressTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverNameTxt != null : "fx:id=\"receiverNameTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverLastNameTxt != null : "fx:id=\"receiverLastNameTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverPhoneNumberTxt != null : "fx:id=\"receiverPhoneNumberTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverEmailTxt != null : "fx:id=\"receiverEmailTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverLockerAddressTxt != null : "fx:id=\"receiverLockerAddressTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sizeOfPackage != null : "fx:id=\"sizeOfPackage\" was not injected: check your FXML file 'clientView.fxml'.";
        assert smallSize != null : "fx:id=\"smallSize\" was not injected: check your FXML file 'clientView.fxml'.";
        assert mediumSize != null : "fx:id=\"mediumSize\" was not injected: check your FXML file 'clientView.fxml'.";
        assert bigSize != null : "fx:id=\"bigSize\" was not injected: check your FXML file 'clientView.fxml'.";
        assert dollarLabel != null : "fx:id=\"dollarLabel\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiveViewBox != null : "fx:id=\"receiveViewBox\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiveNumberOfPackageTxt != null : "fx:id=\"receiveNumberOfPackageTxt\" was not injected: check your FXML file 'clientView.fxml'.";
        assert packagesShippedTable != null : "fx:id=\"packagesShippedTable\" was not injected: check your FXML file 'clientView.fxml'.";
        assert idColS != null : "fx:id=\"idColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sizeColS != null : "fx:id=\"sizeColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert shipmentDateColS != null : "fx:id=\"shipmentDateColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert collectionDateColS != null : "fx:id=\"collectionDateColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert priceColS != null : "fx:id=\"priceColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert senderColS != null : "fx:id=\"senderColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverColS != null : "fx:id=\"receiverColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert senderLockerColS != null : "fx:id=\"senderLockerColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverLockerColS != null : "fx:id=\"receiverLockerColS\" was not injected: check your FXML file 'clientView.fxml'.";
        assert packagesReceivedTable != null : "fx:id=\"packagesReceivedTable\" was not injected: check your FXML file 'clientView.fxml'.";
        assert idColR != null : "fx:id=\"idColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert sizeColR != null : "fx:id=\"sizeColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert shipmentDateColR != null : "fx:id=\"shipmentDateColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert collectionDateColR != null : "fx:id=\"collectionDateColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert priceColR != null : "fx:id=\"priceColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert senderColR != null : "fx:id=\"senderColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverColR != null : "fx:id=\"receiverColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert senderLockerColR != null : "fx:id=\"senderLockerColR\" was not injected: check your FXML file 'clientView.fxml'.";
        assert receiverLockerColR != null : "fx:id=\"receiverLockerColR\" was not injected: check your FXML file 'clientView.fxml'.";

        loggedAsView.setText("Signed in as: " + UserService.getActiveClient().getName() + " " +
                UserService.getActiveClient().getLastName());
    }
}
