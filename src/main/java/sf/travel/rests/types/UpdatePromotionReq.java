package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.Status;

import java.time.LocalDateTime;

@Data
public class UpdatePromotionReq {
    private String name;
    private String description;
    private int value;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Status status;
}
