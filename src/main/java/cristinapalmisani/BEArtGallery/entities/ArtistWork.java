package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "artist_works")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArtistWork {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Column(name = "name_work")
    private String nameWork;
    @Column(name = "date_start_work")
    private LocalDate dateStartWork;
    private String description;
    private String technique;
    private String image;





}
