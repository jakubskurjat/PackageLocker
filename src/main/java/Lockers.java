import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lockers {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "number_small_lockers")
    private int numberSmallLockers;

    @Column(name = "number_medium_lockers")
    private int numberMediumLockers;

    @Column(name = "number_big_lockers")
    private int numberBigLockers;
}
