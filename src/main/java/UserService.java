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

/**
 * This class represents all user related functionality.
 */
public class UserService {

    /**
     * This private static field represents active client.
     */
    private static Client activeClient;

    /**
     * This private static fields represents active staffer.
     */
    private static Staffer activeStaffer;

    /**
     * This private static field represents <code>Session</code>.
     */
    private static Session session = LaunchWindowController.getFactory().openSession();

    /**
     * This method checks if the client with the given email and password exists.
     * Returns true if the client exists, or false if client does not exist.
     *
     * @param givenEmail    represents email of the client.
     * @param givenPassword represents password of the client.
     * @return true or false.
     */
    public static boolean isClientInDatabase(TextField givenEmail, TextField givenPassword) {
        String query = "FROM Client WHERE email = '" + givenEmail.getText() +
                "' AND password = '" + givenPassword.getText() + "'";

        Optional clientFromDB = session.createQuery(query).uniqueResultOptional();

        return clientFromDB.isPresent();
    }

    /**
     * This method checks if the staffer with the given email and password exists.
     * Returns true if the staffer exists, or false if staffer does not exist.
     *
     * @param givenEmail    represents email of the staffer.
     * @param givenPassword represents password of the staffer.
     * @return true or false.
     */
    public static boolean isStafferInDatabase(TextField givenEmail, TextField givenPassword) {
        String query = "FROM Staffer WHERE email = '" + givenEmail.getText() +
                "' AND password = '" + givenPassword.getText() + "'";

        Optional stafferFromDB = session.createQuery(query).uniqueResultOptional();

        return stafferFromDB.isPresent();
    }

    /**
     * This method adds the client to the database.
     *
     * @param givenName        represents name of the client we want to create.
     * @param givenLastName    represents last name of the client we want to create.
     * @param givenEmail       represents email of the client we want to create.
     * @param givenPhoneNumber represents phone number of the client we want to create.
     * @param givenPassword    represents password of the client we want to create.
     * @param confirmPassword  represents confirm password of the client we want to create.
     */
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

    /**
     * This method sets active client.
     *
     * @param givenEmail    represents email of the active client.
     * @param givenPassword represents password of the active client.
     */
    public static void setActiveClient(TextField givenEmail, TextField givenPassword) {
        Query query = session.createQuery("SELECT id FROM Client WHERE email IN :clientEmail");

        query.setParameter("clientEmail", givenEmail.getText());
        List list = query.list();

        int i = (int) list.get(0);

        activeClient = session.get(Client.class, i);
        clearGivenData(givenEmail, givenPassword);
    }

    /**
     * This method sets active staffer.
     *
     * @param givenEmail    represents email of the active staffer.
     * @param givenPassword represents password of the active staffer.
     */
    public static void setActiveStaffer(TextField givenEmail, TextField givenPassword) {
        Query query = session.createQuery("SELECT id FROM Staffer WHERE email IN :stafferEmail");

        query.setParameter("stafferEmail", givenEmail.getText());
        List list = query.list();

        int i = (int) list.get(0);

        activeStaffer = session.get(Staffer.class, i);
        clearGivenData(givenEmail, givenPassword);
    }

    /**
     * this method with a variable number of parameters clears all given data.
     *
     * @param givenData represents a variable number of parameters to be cleared.
     */
    private static void clearGivenData(TextField... givenData) {
        for (TextField data : givenData)
            data.clear();
    }

    /**
     * This method creates a dialog that allows the user to change the password.
     * If the client enters a changed password and confirms this password,
     * then his password has been changed. If the confirmed password is different than the set password,
     * an appropriate message is displayed. If the customer enters the correct password and confirms it correctly,
     * but asks for a wrong email, he will be informed that there is no client with the given email.
     *
     * @param inEmailClientTxt    represents given email.
     * @param inPasswordClientTxt represents given password.
     */
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
            Optional<Client> userFromDB = session.createQuery("FROM Client WHERE email = '" + inEmailClientTxt.getText() + "'").uniqueResultOptional();

            if (userFromDB.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                preparingDialogPane("ERROR", alert);

                alert.setContentText("The user with the given e-mail \"" + inEmailClientTxt.getText() + "\" does not exist or the wrong password was given.");
                alert.show();
            } else if (!password.getText().equals(confirmPassword.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                preparingDialogPane("ERROR", alert);

                alert.setContentText("The password and confirm password fields are not the same.");
                alert.show();

                alert.setOnHidden(dialogEvent -> changeYourPassword(inEmailClientTxt, inPasswordClientTxt));
            } else {
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

    /**
     * This method prepares every dialog that the user sees.
     *
     * @param headerText represents header text of dialog.
     * @param alert      represents alert that the user sees.
     */
    public static void preparingDialogPane(String headerText, Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText(headerText);
        dialogPane.getStylesheets().add(
                UserService.class.getResource("myDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
    }

    /**
     * This method checks that the entered text is a number.
     * Return true if the entered text is a number or false if
     * the entered text is not a number.
     *
     * @param numbers represents a text field, whose content is being checked.
     * @return true or false.
     */
    public static boolean isNumber(TextField numbers) {
        for (int i = 0; i < numbers.getLength(); i++) {
            if (!(numbers.getText().charAt(i) >= '0' && numbers.getText().charAt(i) <= '9')) {
                return false;
            }
        }

        return true;
    }

    /**
     * This is the <code>activeClient</code> getter.
     *
     * @return <code>activeClient</code>.
     */
    public static Client getActiveClient() {
        return activeClient;
    }

    /**
     * This is the <code>activeStaffer</code> getter.
     *
     * @return <code>activeStaffer</code>.
     */
    public static Staffer getActiveStaffer() {
        return activeStaffer;
    }
}
