package sf.travel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // it's a Annotation
    private long id;

    @Column
    private String email;

    @Column
    private String passWord;

    @Column
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;
}
