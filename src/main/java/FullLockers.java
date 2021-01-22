import lombok.*;

import javax.persistence.Table;

@Table(name = "full_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullLockers {

    private int id;

    private int numberSmallFullLockers;

    private int numberMediumFullLockers;

    private int numberBigFullLockers;
}