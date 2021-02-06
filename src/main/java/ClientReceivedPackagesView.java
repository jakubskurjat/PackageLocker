import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "receivedpackagesview")
@Immutable
public class ClientReceivedPackagesView {
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
   private  double price;

    @Column(name = "sender_name")
   private String senderName;

    @Column(name = "sender_last_name")
    private String senderLastName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_last_name")
    private String receiverLastName;

    @Column(name = "receiver_locker_address")
   private String packageLockerAddress;


    public int getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public double getPrice() {
        return price;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverLastName() {
        return receiverLastName;
    }

    public String getPackageLockerAddress() {
        return packageLockerAddress;
    }
}
