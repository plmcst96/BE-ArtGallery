package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity

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
    @JsonIgnore
    private Artist artist;
    @OneToMany
    @JoinColumn(name = "artistWork_id")
    @ToString.Exclude
    @JsonIgnore
    private List<ArtistWork> artistWorks;

    public Gallery(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

}
