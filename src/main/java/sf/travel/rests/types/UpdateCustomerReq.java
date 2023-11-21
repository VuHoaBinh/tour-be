package sf.travel.rests.types;

import lombok.Data;

@Data
public class UpdateCustomerReq {
    private String name;
    private String email;
    private String dob;
}
