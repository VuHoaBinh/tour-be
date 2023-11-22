package sf.travel.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sf.travel.enums.TravelType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "travels")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String detail;

    // define special column
    @Enumerated(EnumType.STRING)
    @Column
    private TravelType type;

    @Column(nullable = true)
    private String[] categories;

    @Column(nullable = true)
    private String additionalInfo;

    @Column(nullable = true)
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "promotion", nullable = true)
    private Promotion promotion;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
