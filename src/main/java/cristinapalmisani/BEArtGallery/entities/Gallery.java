package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "gallery")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Gallery {
    @Id
    @GeneratedValue
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @OneToMany(mappedBy = "gallery")
    @ToString.Exclude
    @JsonIgnore
    private List<ArtistWork> artistWorks;



}
