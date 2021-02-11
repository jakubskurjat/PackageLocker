import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class represents table <code>total_lockers</code>.
 */
@Entity
@Table(name = "total_lockers")
@Getter
@Setter
public class TotalLockers {

    /**
     * This private field represents <code>id_locker</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private int id;

    /**
     * This private field represents <code>number_small_total_lockers</code> column.
     */
    @NonNull
    @Column(name = "number_small_total_lockers")
    private int numberSmallTotalLockers;

    /**
     * This private field represents <code>number_medium_total_lockers</code> column.
     */
    @NonNull
    @Column(name = "number_medium_total_lockers")
    private int numberMediumTotalLockers;

    /**
     * This private field represents <code>number_big_total_lockers</code> column.
     */
    @NonNull
    @Column(name = "number_big_total_lockers")
    private int numberBigTotalLockers;

    /**
     * This private field represents <code>id_package_lockers</code> column.
     */
    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = PackageLockers.class)
    @JoinColumn(name = "id_package_lockers")
    private PackageLockers packageLockers;
}