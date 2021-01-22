import lombok.*;

import javax.persistence.Table;

@Table(name = "total_lockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TotalLockers {

    private int id;

    private int numberSmallTotalLockers;

    private int numberMediumTotalLockers;

    private int numberBigTotalLockers;
}