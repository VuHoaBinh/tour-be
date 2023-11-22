package sf.travel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // get & set
@AllArgsConstructor // contructor
@NoArgsConstructor // reference
// hibernate
@Entity
@Table(name = "Image")
public class FileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increase ID
    private Long id;
    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] imageByte;
}
