import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * This class represents table <code>shipment</code>.
 */
@Entity
@Table(name = "shipment")
@Getter
@Setter
public class Shipment {

    /**
     * This private field represents <code>id_shipment</code> column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shipment")
    private int id;

    /**
     * This private field represents <code>shipment_date</code> column.
     */
    @NonNull
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    /**
     * This private field represents <code>collection_date</code> column.
     */
    @Column(name = "collection_date")
    private LocalDate collectionDate;

    /**
     * This private field represents <code>id_client_sender_package</code> column.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinColumn(name = "id_client_sender_package")
    private Client sender;

    /**
     * This private field represents <code>id_client_receiver_package</code> column.
     */
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Client.class)
    @JoinColumn(name = "id_client_receiver_package")
    private Client receiver;

    /**
     * This private field represents <code>id_package</code> column.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Package.class)
    @JoinColumn(name = "id_package")
    private Package sentPackage;

    /**
     * This private field represents <code>id_package_locker_sender</code> column.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = PackageLockers.class)
    @JoinColumn(name = "id_package_locker_sender")
    private PackageLockers packageLockersReceiver;

    /**
     * This private field represents <code>id_package_locker_receiver</code> column.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = PackageLockers.class)
    @JoinColumn(name = "id_package_locker_receiver")
    private PackageLockers packageLockersSender;
}