import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * This class represents <code>SessionFactory</code>.
 */
public class SessionFactoryCreator {

    /**
     * This private static field represents <code>SessionFactory</code>.
     */
    private static SessionFactory factory;

    /**
     * This static method creates <code>SessionFactory</code>.
     *
     * @return created session factory.
     */
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