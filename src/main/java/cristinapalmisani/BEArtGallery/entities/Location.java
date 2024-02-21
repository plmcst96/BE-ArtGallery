package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String address;
    private String city;
    private int zipCode;
    private String nation;
    @Column(name = "museum_name")
    private String museumName;

    @OneToOne
    @JoinColumn(name = "artist_work_id")
    @JsonIgnore
    private ArtistWork artistWork;
    @OneToOne
    @JoinColumn(name = "exhibition_id")
    @JsonIgnore
    private Exhibition exhibition;
}
