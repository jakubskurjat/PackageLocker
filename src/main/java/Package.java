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
    @Column(name = "id_pack")
    private int id;

    @NonNull
    @Column(name = "size")
    private Size size;

    @NonNull
    @Column(name = "price")
    private double price;

//    @OneToOne
//    @JoinColumn(name = "id_shipment")
//    private Shipment shipment;

    @NonNull
    @OneToOne
    @JoinColumn(name = "id_locker")
    private Locker locker;
}