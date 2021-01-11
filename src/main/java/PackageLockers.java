import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PackageLockers {

    private int id;

    private String addressLocker;

    private TotalLockers totalLockers;

    private FullLockers fullLockers;
}