import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static Client activeClient;
    private static Staffer activeStaffer;

    public static boolean isClientInDatabase(TextField givenEmail, TextField givenPassword){
        Session session = LaunchWindowController.getFactory().openSession();

        String query = "FROM Client WHERE email = '" + givenEmail.getText() +
                "' AND password = '" + givenPassword.getText() + "'";

        Optional clientFromDB = session.createQuery(query).uniqueResultOptional();

        return clientFromDB.isPresent();
    }

    public static boolean isStafferInDatabase(TextField givenEmail, TextField givenPassword){
        Session session = LaunchWindowController.getFactory().openSession();

        String query = "FROM Staffer WHERE email = '" + givenEmail.getText() +
                "' AND password = '" + givenPassword.getText() + "'";

        Optional stafferFromDB = session.createQuery(query).uniqueResultOptional();

        return stafferFromDB.isPresent();
    }

    public static void addClient(TextField givenName, TextField givenLastName, TextField givenEmail,
                                 TextField givenPhoneNumber, TextField givenPassword,TextField confirmPassword){

        Session session = LaunchWindowController.getFactory().openSession();

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

    public static void setActiveClient(TextField givenEmail, TextField givenPassword){
        Session session = LaunchWindowController.getFactory().openSession();

        Query query = session.createQuery("SELECT id FROM Client WHERE email IN :clientEmail");

        query.setParameter("clientEmail", givenEmail.getText());
        List list = query.list();

        int i = (int) list.get(0);

        activeClient = session.get(Client.class, i );
        clearGivenData(givenEmail, givenPassword);
    }

    public static void setActiveStaffer(TextField givenEmail, TextField givenPassword){
        Session session = LaunchWindowController.getFactory().openSession();

        Query query = session.createQuery("SELECT id FROM Staffer WHERE email IN :stafferEmail");

        query.setParameter("stafferEmail", givenEmail.getText());
        List list = query.list();

        int i = (int) list.get(0);

        activeStaffer = session.get(Staffer.class, i );
        clearGivenData(givenEmail, givenPassword);
    }

    private static void clearGivenData(TextField ... givenData){
        for(TextField data : givenData)
            data.clear();
    }

    public static Client getActiveClient() {
        return activeClient;
    }

    public static Staffer getActiveStaffer() {
        return activeStaffer;
    }
}
