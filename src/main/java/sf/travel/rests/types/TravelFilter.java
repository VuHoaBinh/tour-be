package sf.travel.rests.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sf.travel.enums.TravelType;

@EqualsAndHashCode(callSuper = true)
@Data
public class TravelFilter extends PaginationParams{
    private String name;
    private TravelType type;
}
