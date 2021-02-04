import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "size")
    private Size size;

    @NonNull
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

    @NonNull
    @Column(name = "price")
    private double price;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @Column(name = "id_package_locker_sender")
    private PackageLockers packageLockerSender;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_package_locker_receiver")
    private PackageLockers packageLockerReceiver;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client_sender_packages")
    private Client sender;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_client_receiver_packages")
    private Client receiver;

    @NonNull
    @OneToOne
    @JoinColumn(name = "id_locker")
    private Locker locker;
}