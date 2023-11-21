package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.HotelType;

@Data
public class UpdateHotelReq {
    private String name;
    private HotelType type;
    private String description;
    private int price = -1;
}
