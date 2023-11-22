package sf.travel.rests.types;

import com.sun.istack.NotNull;
import lombok.Data;
import sf.travel.enums.Status;

@Data
public class UpdateOrderReq {
    private String name;
    private String description;
    private int price;
    private Status status;
}
