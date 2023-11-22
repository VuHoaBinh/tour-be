package sf.travel.rests.types;

import lombok.Data;

@Data
public class CreateUserReq {
    private String id;
    private String email;
    private String passWord;
    private String fullName;
    private String role;
}
