import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Package {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "size")
    private Size size;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

    @Column(name = "price")
    private double price;

    private PackageLockers packageLocker;

    private Client sender;

    private Client receiver;
}