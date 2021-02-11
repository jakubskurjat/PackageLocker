import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * This class represents table <code>clients</code>.
 */
@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    /**
     * This private field represents <code>id_client</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private int id;

    /**
     * This private field represents <code>name_client</code> column.
     */
    @NonNull
    @Column(name = "name_client")
    private String name;

    /**
     * This private field represents <code>last_name_client</code> column.
     */
    @NonNull
    @Column(name = "last_name_client")
    private String lastName;

    /**
     * This private field represents <code>email_client</code> column.
     */
    @NonNull
    @Column(name = "email_client")
    private String email;

    /**
     * This private field represents <code>phone_number_client</code> column.
     */
    @NonNull
    @Column(name = "phone_number_client")
    private BigInteger phoneNumber;

    /**
     * This private field represents <code>password_client</code> column.
     */
    @NonNull
    @Column(name = "password_client")
    private String password;
}