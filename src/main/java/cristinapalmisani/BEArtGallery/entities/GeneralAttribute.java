package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class GeneralAttribute {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String title;
    private String description;
    private String image;
}
