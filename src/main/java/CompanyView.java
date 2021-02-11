import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * This class represents <code>companyview</code>.
 */
@Entity
@Table(name = "companyview")
@Immutable
@Getter
@Setter
public class CompanyView {

    /**
     * This private field represents <code>id_pack</code> column.
     */
    @Id
    @Column(name = "id_pack")
    private int id;

    /**
     * This private field represents <code>size</code> column.
     */
    @Column(name = "size")
    private String size;

    /**
     * This private field represents <code>shipment_date</code> column.
     */
    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    /**
     * This private field represents <code>collection_date</code> column.
     */
    @Column(name = "collection_date")
    private LocalDate collectionDate;

    /**
     * This private field represents <code>price</code> column.
     */
    @Column(name = "price")
    private double price;

    /**
     * This private field represents <code>sender</code> column.
     */
    @Column(name = "sender")
    private String sender;

    /**
     * This private field represents <code>receiver</code> column.
     */
    @Column(name = "receiver")
    private String receiver;

    /**
     * This private field represents <code>sender_locker_address</code> column.
     */
    @Column(name = "sender_locker_address")
    private String packageLockerAddressSender;

    /**
     * This private field represents <code>receiver_locker_address</code> column.
     */
    @Column(name = "receiver_locker_address")
    private String packageLockerAddressReceiver;

    /**
     * This private field represents <code>id_locker</code> column.
     */
    @Column(name = "id_locker")
    private String locker;
}
