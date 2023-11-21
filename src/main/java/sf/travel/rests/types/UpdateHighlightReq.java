package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.HighlightStatus;
import sf.travel.enums.Rank;

import java.time.LocalDateTime;

@Data
public class UpdateHighlightReq {
    private Rank rank;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private HighlightStatus status;
    private Long travelId;
}
