import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "name_client")
    private String name;

    @NonNull
    @Column(name = "last_name_client")
    private String lastName;

    @NonNull
    @Column(name = "email_client")
    private String email;

    @NonNull
    @Column(name = "phone_number_client")
    private BigInteger phoneNumber;

    @NonNull
    @Column(name = "password_client")
    private String password;
}