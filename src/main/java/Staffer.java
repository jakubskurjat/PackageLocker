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
public class Staffer
{
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name_staffer")
    private String name;

    @Column(name = "last_name_staffer")
    private String lastName;

    @Column(name = "email_staffer")
    private String email;

    @Column(name = "phone_number_staffer")
    private BigInteger phoneNumber;

    @Column(name = "password_staffer")
    private String password;
}