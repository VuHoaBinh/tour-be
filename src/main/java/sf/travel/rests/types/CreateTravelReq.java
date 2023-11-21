package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.TravelType;

import javax.validation.constraints.NotNull;

@Data
public class CreateTravelReq {
    @NotNull
    private String name;
    private String description;
    private String detail;
    @NotNull
    private TravelType type;
    private String[] categories;
    private String additionalInfo;
    private String  metadata;
}
