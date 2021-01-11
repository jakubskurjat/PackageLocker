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
    @GeneratedValue
    private int id;

    @Column(name = "name_client")
    private String name;

    @Column(name = "last_name_client")
    private String lastName;

    @Column(name = "email_client")
    private String email;

    @Column(name = "phone_number_client")
    private BigInteger phoneNumber;

    @Column(name = "password_client")
    private String password;
}
