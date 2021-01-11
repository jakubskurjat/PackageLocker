import lombok.*;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client
{
    private int id;

    private String name;

    private String lastName;

    private String email;

    private BigInteger phoneNumber;

    private String password;
}