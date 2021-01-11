import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "total_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TotalLockers {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "number_small_total_lockers")
    private int numberSmallTotalLockers;

    @Column(name = "number_medium_total_lockers")
    private int numberMediumTotalLockers;

    @Column(name = "number_big_total_lockers")
    private int numberBigTotalLockers;
}