import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    @OneToOne
    private PackageLockers packageLocker;

    @NonNull
    @ManyToOne
    private Client sender;

    @NonNull
    @ManyToOne
    private Client receiver;
}