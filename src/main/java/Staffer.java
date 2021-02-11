import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * This class represents table <code>staffers</code>.
 */
@Entity
@Table(name = "staffers")
@Getter
@Setter
public class Staffer {

    /**
     * This private field represents <code>id_staffer</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_staffer")
    private int id;

    /**
     * This private field represents <code>name_staffer</code> column.
     */
    @NonNull
    @Column(name = "name_staffer")
    private String name;

    /**
     * This private field represents <code>last_name_staffer</code> column.
     */
    @NonNull
    @Column(name = "last_name_staffer")
    private String lastName;

    /**
     * This private field represents <code>email_staffer</code> column.
     */
    @NonNull
    @Column(name = "email_staffer")
    private String email;

    /**
     * This private field represents <code>phone_number_staffer</code> column.
     */
    @NonNull
    @Column(name = "phone_number_staffer")
    private BigInteger phoneNumber;

    /**
     * This private field represents <code>password_staffer</code> column.
     */
    @NonNull
    @Column(name = "password_staffer")
    private String password;
}