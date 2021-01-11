import lombok.*;

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