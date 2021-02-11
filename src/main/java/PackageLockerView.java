import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * This class represents <code>packagelockerview</code>.
 */
@Entity
@Table(name = "packagelockerview")
@Immutable
@Getter
@Setter
public class PackageLockerView {

    /**
     * This private field represents <code>id_locker</code> column.
     */
    @Id
    @Column(name = "id_locker")
    private int idLocker;

    /**
     * This private field represents <code>isEmpty</code> column.
     */
    @Column(name = "isEmpty")
    private String isEmpty;

    /**
     * This private field represents <code>id_package_lockers</code> column.
     */
    @Column(name = "id_package_lockers")
    private int idPackageLockers;

    /**
     * This private field represents <code>shipment_date</code> column.
     */
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;
}