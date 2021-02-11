import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class represents the controller for the staffer view.
 */
public class StafferViewController {

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

    /**
     * This private field represents <code>Alert</code>.
     */
    private Alert alert;

    /**
     * This method shows the staffer the profit on the selected day from all package lockers.
     * It uses a method created in MySql. When the staffer does not give a date,
     * an alert pops up.
     *
     * @param event represents <code>ActionEvent</code>.
     */
    @FXML
    void onShowProfitClicked(ActionEvent event) {
        try {
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
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Please select a date.");
            alert.show();
        }
    }

    /**
     * This method shows the staffer the status of the package lockers.
     * When the staffer does not give a date or incorrect id an alert pops up.
     *
     * @param event represents <code>ActionEvent</code>.
     */
    @FXML
    void onShowStatsClicked(ActionEvent event) {
        try {
            String queryLockers = "SELECT DISTINCT * FROM packageLockerView WHERE id_package_lockers = " + statsPackageLockerTxt.getText()
                    + " AND shipment_date = '" + statsDatePicker.getValue().toString() + "'";

            ViewService.preparingTableViewForPackageLocker(queryLockers, packageLockerView, idLockerCol, isEmptyLockerCol);
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            UserService.preparingDialogPane("ERROR", alert);
            alert.setContentText("Please select a date or give correct id of package locker.");
            alert.show();
        }
    }

    /**
     * This method shows the staffer all packages that were and are from all package lockers.
     *
     * @param event represents <code>ActionEvent</code>.
     */
    @FXML
    void onShowAllPackagesClicked(ActionEvent event) {
        String queryAllPackageView = "SELECT * FROM CompanyView";

        ViewService.preparingTableViewForStaffers(queryAllPackageView, companyPackagesView, idColS, sizeColS, shipmentDateColS, collectionDateColS, priceColS, senderColS, receiverColS, senderLockerColS, receiverLockerColS, idLockerColS);
    }

    /**
     * This method allows the staffer to sign out.
     *
     * @param event represents <code>ActionEvent</code>.
     */
    @FXML
    public void onSignOut(ActionEvent event) {
        Stage stage1 = (Stage) signOutStafferButton.getScene().getWindow();
        stage1.close();
    }

    /**
     * This method is called when creating the staffer view.
     * It shows who is signed in.
     * It creates a view of the addresses of all package lockers.
     */
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

        ViewService.preparingTableViewForAddressesOfPackageLockers(addressesTable, idPackageLockerCol, addressPackageLockerCol);
    }

}
