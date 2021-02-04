import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "package_archive")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PackageArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pack_archive")
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
}
