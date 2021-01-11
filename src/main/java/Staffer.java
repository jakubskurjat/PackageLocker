import lombok.*;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Staffer
{
    private int id;

    private String name;

    private String lastName;

    private String email;

    private BigInteger phoneNumber;

    private String password;
}