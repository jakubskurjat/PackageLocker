import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lockers")
@Getter
@Setter
public class Locker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private int id;

    @NonNull
    @Column(name = "size")
    private Size size;

    @NonNull
    @Column(name = "isEmpty")
    private boolean isEmpty;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_package_lockers")
    private Package packageLockers;
}