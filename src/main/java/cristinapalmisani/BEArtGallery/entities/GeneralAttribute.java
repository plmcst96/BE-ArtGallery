package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter

@NoArgsConstructor
public abstract class GeneralAttribute {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String title;
    @Lob
    private String description;
    @ToString.Exclude
    private List<String> image;

    public GeneralAttribute(String title, String description) {
        this.title = title;
        this.description = description;

    }
}
