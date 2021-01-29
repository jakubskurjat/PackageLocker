import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class SessionFactoryCreator {

    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        try {
            factory = new Configuration().configure(new File("target/classes/hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        return factory;
    }
}