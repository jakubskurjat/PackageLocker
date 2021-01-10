import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "senders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sender
{
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name_sender")
    private String name;

    @Column(name = "last_name_sender")
    private String lastName;

    @Column(name = "email_sender")
    private String email;

    @Column(name = "tel_number_sender")
    private BigInteger tel_number;
}
