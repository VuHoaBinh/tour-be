package sf.travel.rests.types;

import lombok.Data;

@Data
public class CreateCustomerReq {
    private String name;
    private String email;
    private String dob;
}
