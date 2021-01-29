import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;

public class UserService {

    public static boolean isClientInDatabase(TextField givenEmail, TextField givenPassword){
        Session session = LaunchWindowController.getFactory().openSession();

        Query query = session.createQuery("SELECT count(*) FROM Client WHERE email IN :clientEmail AND password IN:clientPassword");

        query.setParameter("clientEmail", givenEmail.getText());
        query.setParameter("clientPassword", givenPassword.getText());

        clearGivenData(givenEmail, givenPassword);

        return (Integer.parseInt(query.getSingleResult().toString()) == 1)
                ? true
                : false;
    }

    public static void addClient(TextField givenName, TextField givenLastName, TextField givenEmail,
                                 TextField givenPhoneNumber, TextField givenPassword){

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

        clearGivenData(givenName, givenLastName, givenEmail, givenPhoneNumber, givenPassword);
    }

    private static void clearGivenData(TextField ... givenData){
        for(TextField data : givenData)
            data.clear();
    }
}
