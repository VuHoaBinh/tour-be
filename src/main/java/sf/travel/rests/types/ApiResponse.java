package sf.travel.rests.types;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse<T> {
    private List<T> items;
    private long total;
    private int size;
    private int page;
}
