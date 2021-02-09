import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "packagelockerview")
@Immutable
@Getter
@Setter
public class PackageLockerView {

    @Id
    @Column(name = "id_locker")
    private int idLocker;

    @Column(name = "isEmpty")
    private String isEmpty;

    @Column(name = "id_package_lockers")
    private int idPackageLockers;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;
}