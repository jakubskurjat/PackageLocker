import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "staffers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Staffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_staffer")
    private int id;

    @NonNull
    @Column(name = "name_staffer")
    private String name;

    @NonNull
    @Column(name = "last_name_staffer")
    private String lastName;

    @NonNull
    @Column(name = "email_staffer")
    private String email;

    @NonNull
    @Column(name = "phone_number_staffer")
    private BigInteger phoneNumber;

    @NonNull
    @Column(name = "password_staffer")
    private String password;
}