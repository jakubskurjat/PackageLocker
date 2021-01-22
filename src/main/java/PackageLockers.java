import lombok.*;

import javax.persistence.*;

@Table(name = "package_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PackageLockers {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "address_locker")
    private String addressLocker;

    private TotalLockers totalLockers;

    private FullLockers fullLockers;
}