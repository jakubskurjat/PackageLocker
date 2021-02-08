import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "companyview")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyView {
    @Id
    @Column(name = "id_pack")
    private int id;

    @Column(name = "size")
    private String size;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

    @Column(name = "price")
    private double price;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "sender_locker_address")
    private String packageLockerAddressSender;

    @Column(name = "receiver_locker_address")
    private String packageLockerAddressReceiver;

    @Column(name = "id_locker")
    private String locker;
}
