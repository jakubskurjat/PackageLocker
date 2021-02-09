import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class StafferViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text loggedAsStafferView;

    @FXML
    private Button signOutStafferButton;

    @FXML
    private TableView<PackagesView> companyPackagesView;

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
    private TableColumn<?, ?> idLockerColS;

    @FXML
    private DatePicker profitDatePicker;

    @FXML
    private Text profitResultText;

    @FXML
    private TableView<AddressesView> addressesTable;

    @FXML
    private TableColumn<?, ?> idPackageLockerCol;

    @FXML
    private TableColumn<?, ?> addressPackageLockerCol;

    @FXML
    private TextField statsPackageLockerTxt;

    @FXML
    private DatePicker statsDatePicker;

    @FXML
    private TableView<PackageLockerView> packageLockerView;

    @FXML
    private TableColumn<?, ?> idLockerCol;

    @FXML
    private TableColumn<?, ?> isEmptyLockerCol;

    private Alert alert;

    @FXML
    void onShowProfitClicked(ActionEvent event) {
        if (profitDatePicker.getValue() == null) {
            alert.setContentText("Please select a date.");
            alert.show();
        } else {
            Session session = SessionFactoryCreator.getFactory().openSession();

            AtomicReference<Double> profit = new AtomicReference<>();

            session.doWork(connection -> {
                try (CallableStatement callableStatement = connection.prepareCall(
                        "{ ? = call profitCalculator(?) }")) {
                    callableStatement.registerOutParameter(1, Types.DECIMAL);
                    callableStatement.setString(2, profitDatePicker.getValue().toString());
                    callableStatement.execute();
                    profit.set(callableStatement.getDouble(1));
                }
            });

            profitResultText.setText("Profit: " + profit + " $");
        }
    }

    @FXML
    void onShowStatsClicked(ActionEvent event) {
        String queryLockers = "SELECT * FROM packageLockerView WHERE id_package_lockers = " + statsPackageLockerTxt.getText()
                + " AND shipment_date = '" + statsDatePicker.getValue().toString() + "'";

        ViewService.preparingTableViewForPackageLocker(queryLockers, packageLockerView, idLockerCol, isEmptyLockerCol);
    }

    @FXML
    void onShowAllPackagesClicked(ActionEvent event) {
        String queryAllPackageView = "SELECT * FROM CompanyView";

        ViewService.preparingTableViewForStaffers(queryAllPackageView, companyPackagesView, idColS, sizeColS, shipmentDateColS, collectionDateColS, priceColS, senderColS, receiverColS, senderLockerColS, receiverLockerColS, idLockerColS);
    }

    @FXML
    public void onSignOut(ActionEvent actionEvent) {
        Stage stage1 = (Stage) signOutStafferButton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void initialize() {
        assert loggedAsStafferView != null : "fx:id=\"loggedAsStafferView\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert signOutStafferButton != null : "fx:id=\"signOutStafferButton\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert companyPackagesView != null : "fx:id=\"companyPackagesView\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert idColS != null : "fx:id=\"idColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert sizeColS != null : "fx:id=\"sizeColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert shipmentDateColS != null : "fx:id=\"shipmentDateColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert collectionDateColS != null : "fx:id=\"collectionDateColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert priceColS != null : "fx:id=\"priceColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderColS != null : "fx:id=\"senderColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverColS != null : "fx:id=\"receiverColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderLockerColS != null : "fx:id=\"senderLockerColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverLockerColS != null : "fx:id=\"receiverLockerColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert idLockerColS != null : "fx:id=\"idLockerColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert profitDatePicker != null : "fx:id=\"profitDatePicker\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert profitResultText != null : "fx:id=\"profitResultText\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert statsPackageLockerTxt != null : "fx:id=\"statsPackageLockerTxt\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert statsDatePicker != null : "fx:id=\"statsDatePicker\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert packageLockerView != null : "fx:id=\"packageLockerView\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert idLockerCol != null : "fx:id=\"idLockerCol\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert isEmptyLockerCol != null : "fx:id=\"isEmptyLockerCol\" was not injected: check your FXML file 'stafferView.fxml'.";


        loggedAsStafferView.setText("Signed in as: " + UserService.getActiveStaffer().getName() + " " +
                UserService.getActiveStaffer().getLastName());

        alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText("Error");
        dialogPane.getStylesheets().add(
                getClass().getResource("myDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        ViewService.preparingTableViewForAddressesOfPackageLockers(addressesTable,idPackageLockerCol,addressPackageLockerCol);
    }
}
