import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;

import javax.swing.*;
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
    private DatePicker profitDatePicker;

    @FXML
    private Text profitResultText;

    @FXML
    private TextField statsPackageLockerTxt;

    @FXML
    private DatePicker statsDatePicker;

    @FXML
    private Text loggedAsStafferView;

    @FXML
    private Button signOutStafferButton;

    @FXML
    private TableColumn<?, ?> idLocker;

    @FXML
    private TableColumn<?, ?> isEmptyLocker;

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

    }

    @FXML
    void onShowAllPackagesClicked(ActionEvent event) {

    }

    @FXML
    public void onSignOut(ActionEvent actionEvent) {
        Stage stage1 = (Stage) signOutStafferButton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void initialize() {
        assert idColS != null : "fx:id=\"idPackColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert sizeColS != null : "fx:id=\"sizeColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert shipmentDateColS != null : "fx:id=\"shipDateColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert collectionDateColS != null : "fx:id=\"colDateColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert priceColS != null : "fx:id=\"priceColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderColS != null : "fx:id=\"senderIdColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverColS != null : "fx:id=\"senderPlColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderLockerColS != null : "fx:id=\"senderNameColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverLockerColS != null : "fx:id=\"receiverIdColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert profitDatePicker != null : "fx:id=\"profitDatePicker\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert profitResultText != null : "fx:id=\"profitResultText\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert statsPackageLockerTxt != null : "fx:id=\"statsPackageLockerTxt\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert statsDatePicker != null : "fx:id=\"statsDatePicker\" was not injected: check your FXML file 'stafferView.fxml'.";

        loggedAsStafferView.setText("Signed in as: " + UserService.getActiveStaffer().getName() + " " +
                UserService.getActiveStaffer().getLastName());

        alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText("Error");
        dialogPane.getStylesheets().add(
                getClass().getResource("myDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

    }

}
