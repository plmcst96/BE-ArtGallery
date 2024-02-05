package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "blogs")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Blog {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String title;
    private String author;
    private LocalDate date;
    private String description;
    private String image;

}
