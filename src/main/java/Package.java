import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Package {

    private int id;

    private Size size;

    private LocalDate shipmentDate;

    private LocalDate collectionDate;

    private PackageLockers packageLocker;

    private Client sender;

    private Client receiver;
}