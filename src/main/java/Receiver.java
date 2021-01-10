import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "receivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Receiver
{
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name_receiver")
    private String name;

    @Column(name = "last_name_receiver")
    private String lastName;

    @Column(name = "email_receiver")
    private String email;

    @Column(name = "tel_number_receiver")
    private BigInteger telNumber;
}
