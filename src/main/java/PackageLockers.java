import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "package_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PackageLockers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "address_locker")
    private String addressLocker;

    @NonNull
    @OneToOne
    private TotalLockers totalLockers;

    @NonNull
    @OneToOne
    private FullLockers fullLockers;
}