import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.hibernate.Session;

import javax.swing.*;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Types;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class StafferViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> idPackColS;

    @FXML
    private TableColumn<?, ?> sizeColS;

    @FXML
    private TableColumn<?, ?> shipDateColS;

    @FXML
    private TableColumn<?, ?> colDateColS;

    @FXML
    private TableColumn<?, ?> priceColS;

    @FXML
    private TableColumn<?, ?> senderIdColS;

    @FXML
    private TableColumn<?, ?> senderPlColS;

    @FXML
    private TableColumn<?, ?> senderNameColS;

    @FXML
    private TableColumn<?, ?> receiverIdColS;

    @FXML
    private TableColumn<?, ?> receiverPlColS;

    @FXML
    private TableColumn<?, ?> receiverColS;

    @FXML
    private DatePicker profitDatePicker;

    @FXML
    private Text profitResultText;

    @FXML
    private TextField statsPackageLockerTxt;

    @FXML
    private DatePicker statsDatePicker;

    @FXML
    void onShowProfitClick(ActionEvent event) {
        if (profitDatePicker.getValue() == null) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Please select a date.");
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
    void onShowStatsClick(ActionEvent event) {

    }

    @FXML
    void showAllPackages(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert idPackColS != null : "fx:id=\"idPackColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert sizeColS != null : "fx:id=\"sizeColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert shipDateColS != null : "fx:id=\"shipDateColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert colDateColS != null : "fx:id=\"colDateColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert priceColS != null : "fx:id=\"priceColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderIdColS != null : "fx:id=\"senderIdColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderPlColS != null : "fx:id=\"senderPlColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert senderNameColS != null : "fx:id=\"senderNameColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverIdColS != null : "fx:id=\"receiverIdColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverPlColS != null : "fx:id=\"receiverPlColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert receiverColS != null : "fx:id=\"receiverColS\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert profitDatePicker != null : "fx:id=\"profitDatePicker\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert profitResultText != null : "fx:id=\"profitResultText\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert statsPackageLockerTxt != null : "fx:id=\"statsPackageLockerTxt\" was not injected: check your FXML file 'stafferView.fxml'.";
        assert statsDatePicker != null : "fx:id=\"statsDatePicker\" was not injected: check your FXML file 'stafferView.fxml'.";

    }
}
