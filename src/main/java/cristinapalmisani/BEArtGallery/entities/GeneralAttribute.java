package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GeneralAttribute {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String title;
    private String description;
    private String image;
}
