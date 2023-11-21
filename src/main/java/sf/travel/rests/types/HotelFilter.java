package sf.travel.rests.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sf.travel.enums.HotelType;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotelFilter extends PaginationParams{
    private HotelType type;
}
