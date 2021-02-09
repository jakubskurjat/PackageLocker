import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addressesview")
@Immutable
@Getter
@Setter
public class AddressesView {

    @Id
    @Column(name = "id_package_locker")
    private int idPackageLocker;

    @Column(name = "address_locker")
    private String addressPackageLocker;
}