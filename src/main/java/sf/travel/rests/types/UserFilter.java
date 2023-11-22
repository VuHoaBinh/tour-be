package sf.travel.rests.types;

import lombok.Data;
import sf.travel.entities.Roles;

@Data
public class UserFilter {
    private String id;
    private String email;
    private String passWord;
    private String fullName;
    private Roles role;
}
