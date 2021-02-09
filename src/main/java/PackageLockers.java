import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "package_lockers")
@Getter
@Setter
public class PackageLockers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_package_locker")
    private int id;

    @NonNull
    @Column(name = "address_locker")
    private String addressLocker;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TotalLockers.class)
    @JoinColumn(name = "id_locker_total_lockers")
    private TotalLockers totalLockers;
}