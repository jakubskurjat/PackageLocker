import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private int id;

    @NonNull
    @Column(name = "name_client")
    private String name;

    @NonNull
    @Column(name = "last_name_client")
    private String lastName;

    @NonNull
    @Column(name = "email_client")
    private String email;

    @NonNull
    @Column(name = "phone_number_client")
    private BigInteger phoneNumber;

    @NonNull
    @Column(name = "password_client")
    private String password;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_shipment_sent")
//    private Set<Shipment> sentShipment;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_shipment_received")
//    private Set<Shipment> receivedShipment;
}