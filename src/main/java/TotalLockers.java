import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "total_lockers")
@Getter
@Setter
public class TotalLockers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_locker")
    private int id;

    @NonNull
    @Column(name = "number_small_total_lockers")
    private int numberSmallTotalLockers;

    @NonNull
    @Column(name = "number_medium_total_lockers")
    private int numberMediumTotalLockers;

    @NonNull
    @Column(name = "number_big_total_lockers")
    private int numberBigTotalLockers;

    @NonNull
    @OneToOne(targetEntity = PackageLockers.class)
    @JoinColumn(name = "id_package_lockers")
    private PackageLockers packageLockers;
}