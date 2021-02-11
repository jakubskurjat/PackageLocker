import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class represents table <code>package_lockers</code>.
 */
@Entity
@Table(name = "package_lockers")
@Getter
@Setter
public class PackageLockers {

    /**
     * This private field represents <code>id_package_locker</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_package_locker")
    private int id;

    /**
     * This private field represents <code>address_locker</code> column.
     */
    @NonNull
    @Column(name = "address_locker")
    private String addressLocker;

    /**
     * This private field represents <code>id_locker_total_lockers</code> column.
     */
    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TotalLockers.class)
    @JoinColumn(name = "id_locker_total_lockers")
    private TotalLockers totalLockers;
}