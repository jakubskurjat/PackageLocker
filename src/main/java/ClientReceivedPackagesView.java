import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
   private int idPackage;

    @Column(name = "size")
   private Size size;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;
    @Column(name = "collection_date")
   private LocalDate collectionDate;

    @Column(name = "price")
   private  double price;

    @Column(name = "sender name")
   private String senderName;

    @Column(name = "sender last name")
    private String senderLastName;

    @Column(name = "receiver name")
    private String receiverName;

    @Column(name = "receiver last name")
    private String receiverLastName;

    @Column(name = "receiver locker address")
   private String packageLockerAddress;


    public int getIdPackage() {
        return idPackage;
    }

    public Size getSize() {
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
