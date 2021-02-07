import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "package_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PackageLockers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_package_locker")
    private int id;

    @NonNull
    @Column(name = "address_locker")
    private String addressLocker;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = TotalLockers.class)
    @JoinColumn(name = "id_locker_total_lockers")
    private TotalLockers totalLockers;

//    @NonNull
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = FullLockers.class)
//    @JoinColumn(name = "id_locker_full_lockers")
//    private FullLockers fullLockers;

//    @OneToMany
//    @JoinColumn(name = "id_package_locker_lockers")
//    private Set<Locker> lockers;
}