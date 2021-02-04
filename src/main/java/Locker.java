import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @OneToOne(cascade = CascadeType.ALL)
    private PackageLockers packageLockers;
}
