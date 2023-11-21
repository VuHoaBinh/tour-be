package sf.travel.rests.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sf.travel.enums.HighlightStatus;
import sf.travel.enums.Rank;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class HighlightFilter extends PaginationParams{
    private Rank rank;
    private LocalDateTime searchDate;
    private HighlightStatus status;
}
