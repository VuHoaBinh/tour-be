package sf.travel.rests.types;

import lombok.Data;
import javax.validation.constraints.Min;

import javax.persistence.MappedSuperclass;

@Data
public class PaginationParams {
    @Min(0)
    private int page = 0;
    @Min(1)
    private int size = 20;

    private String searchText;
}
