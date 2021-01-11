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
    @GeneratedValue
    private int id;

    @Column(name = "number_small_full_lockers")
    private int numberSmallFullLockers;

    @Column(name = "number_medium_full_lockers")
    private int numberMediumFullLockers;

    @Column(name = "number_big_full_lockers")
    private int numberBigFullLockers;
}
