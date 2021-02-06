import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shipment")
    private int id;

    @NonNull
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client_sender_package")
    private Client sender;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client_receiver_package")
    private Client receiver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_package")
    private Package sentPackage;

    @ManyToOne
    @JoinColumn(name = "id_package_locker_sender")
    private PackageLockers packageLockersReceiver;

    @ManyToOne
    @JoinColumn(name = "id_package_locker_receiver")
    private PackageLockers packageLockersSender;
}
