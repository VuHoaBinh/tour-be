package sf.travel.rests.types;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sf.travel.enums.Status;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class PromotionFilter extends PaginationParams{
    private String name;
    private int value = -1;
    private LocalDateTime searchDate;
    private Status status;
}
