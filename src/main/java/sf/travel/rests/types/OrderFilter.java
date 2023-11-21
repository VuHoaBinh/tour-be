package sf.travel.rests.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sf.travel.enums.Status;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderFilter extends PaginationParams{
    private String name;
    private int price = -1;
    private Status status;
}
