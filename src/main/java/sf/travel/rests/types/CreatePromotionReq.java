package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreatePromotionReq {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private int value;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    @NotNull
    private Status status;
}
