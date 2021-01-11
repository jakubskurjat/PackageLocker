import lombok.*;

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