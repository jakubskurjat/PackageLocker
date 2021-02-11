import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents <code>addressesview</code>.
 */
@Entity
@Table(name = "addressesview")
@Immutable
@Getter
@Setter
public class AddressesView {

    /**
     * This private field represents <code>id_package_locker</code> column.
     */
    @Id
    @Column(name = "id_package_locker")
    private int idPackageLocker;

    /**
     * This private field represents <code>address_locker</code> column.
     */
    @Column(name = "address_locker")
    private String addressPackageLocker;
}