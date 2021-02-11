import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class represents table <code>lockers</code>.
 */
@Entity
@Table(name = "lockers")
@Getter
@Setter
public class Locker {

    /**
     * This private field represents <code>id_locker</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private int id;

    /**
     * This private field represents <code>size</code> column.
     */
    @NonNull
    @Column(name = "size")
    private String size;

    /**
     * This private field represents <code>isEmpty</code> column.
     */
    @NonNull
    @Column(name = "isEmpty")
    private boolean isEmpty;

    /**
     * This private field represents <code>id_package_lockers</code> column.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Package.class)
    @JoinColumn(name = "id_package_lockers")
    private Package packageLockers;
}