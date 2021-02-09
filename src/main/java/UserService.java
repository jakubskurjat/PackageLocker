import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static Client activeClient;
    private static Staffer activeStaffer;
    private static Session session = LaunchWindowController.getFactory().openSession();

    public static boolean isClientInDatabase(TextField givenEmail, TextField givenPassword) {
        String query = "FROM Client WHERE email = '" + givenEmail.getText() +
                "' AND password = '" + givenPassword.getText() + "'";

        Optional clientFromDB = session.createQuery(query).uniqueResultOptional();

        return clientFromDB.isPresent();
    }

    public static boolean isStafferInDatabase(TextField givenEmail, TextField givenPassword) {
        String query = "FROM Staffer WHERE email = '" + givenEmail.getText() +
                "' AND password = '" + givenPassword.getText() + "'";

        Optional stafferFromDB = session.createQuery(query).uniqueResultOptional();

        return stafferFromDB.isPresent();
    }

    public static void addClient(TextField givenName, TextField givenLastName, TextField givenEmail,
                                 TextField givenPhoneNumber, TextField givenPassword, TextField confirmPassword) {
        session.beginTransaction();

        Client client = new Client();
        client.setName(givenName.getText());
        client.setLastName(givenLastName.getText());
        client.setEmail(givenEmail.getText());
        client.setPhoneNumber(BigInteger.valueOf(Long.parseLong(givenPhoneNumber.getText())));
        client.setName(givenName.getText());
        client.setPassword(givenPassword.getText());

        session.save(client);

        session.getTransaction().commit();

        clearGivenData(givenName, givenLastName, givenEmail, givenPhoneNumber, givenPassword, confirmPassword);
    }

    public static void setActiveClient(TextField givenEmail, TextField givenPassword) {
        Query query = session.createQuery("SELECT id FROM Client WHERE email IN :clientEmail");

        query.setParameter("clientEmail", givenEmail.getText());
        List list = query.list();

        int i = (int) list.get(0);

        activeClient = session.get(Client.class, i);
        clearGivenData(givenEmail, givenPassword);
    }

    public static void setActiveStaffer(TextField givenEmail, TextField givenPassword) {
        Query query = session.createQuery("SELECT id FROM Staffer WHERE email IN :stafferEmail");

        query.setParameter("stafferEmail", givenEmail.getText());
        List list = query.list();

        int i = (int) list.get(0);

        activeStaffer = session.get(Staffer.class, i);
        clearGivenData(givenEmail, givenPassword);
    }

    private static void clearGivenData(TextField... givenData) {
        for (TextField data : givenData)
            data.clear();
    }

    public static void changeYourPassword(TextField inEmailClientTxt, TextField inPasswordClientTxt) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Change password");
        dialog.setHeaderText("The user with the given password does not exist.");
        ImageView image = new ImageView("user.jpeg");
        image.setFitHeight(50);
        image.setFitWidth(50);
        dialog.setGraphic(image);

        ButtonType loginButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm password:");

        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);
        grid.add(new Label("Confirm Password:"), 0, 1);
        grid.add(confirmPassword, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(password.getText(), confirmPassword.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            Optional<Client> userFromDB = session.createQuery("FROM Client WHERE email = '"  + inEmailClientTxt.getText() + "'").uniqueResultOptional();

            if(userFromDB.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                preparingDialogPane("ERROR", alert);

                alert.setContentText("The user with the given e-mail \"" + inEmailClientTxt.getText() + "\" does not exist or the wrong password was given.");
                alert.show();
            }else if(!password.getText().equals(confirmPassword.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                preparingDialogPane("ERROR", alert);

                alert.setContentText("The password and confirm password fields are not the same.");
                alert.show();

                alert.setOnHidden(dialogEvent -> changeYourPassword(inEmailClientTxt, inPasswordClientTxt));
            }
            else {
                userFromDB.get().setPassword(password.getText());
                session.beginTransaction().commit();
                clearGivenData(inPasswordClientTxt);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                preparingDialogPane("INFORMATION", alert);

                alert.setContentText("The password changed successfully.");
                alert.show();
            }
        });
    }

    private static void preparingDialogPane(String headerText, Alert alert){
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(headerText);
        dialogPane.getStylesheets().add(
                UserService.class.getResource("myDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
    }

    public static Client getActiveClient() {
        return activeClient;
    }

    public static Staffer getActiveStaffer() {
        return activeStaffer;
    }
}
