import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "full_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullLockers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private int id;

    @NonNull
    @Column(name = "number_small_full_lockers")
    private int numberSmallFullLockers;

    @NonNull
    @Column(name = "number_medium_full_lockers")
    private int numberMediumFullLockers;

    @NonNull
    @OneToOne(targetEntity = PackageLockers.class)
    @JoinColumn(name = "id_package_lockers")
    private PackageLockers packageLockers;
}