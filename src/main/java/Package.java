import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class represents table <code>packages</code>.
 */
@Entity
@Table(name = "packages")
@Getter
@Setter
public class Package {

    /**
     * This private field represents <code>id_pack</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pack")
    private int id;

    /**
     * This private field represents <code>size</code> column.
     */
    @NonNull
    @Column(name = "size")
    private String size;

    /**
     * This private field represents <code>price</code> column.
     */
    @NonNull
    @Column(name = "price")
    private double price;

    /**
     * This private field represents <code>id_locker</code> column.
     */
    @NonNull
    @OneToOne
    @JoinColumn(name = "id_locker")
    private Locker locker;
}