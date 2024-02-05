package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "artists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Artist {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String name;
    private String surname;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "die_date")
    private LocalDate dieDate;
    @Column(name = "history_artist")
    private String historyArtist;
    @Column(name = "image_artist")
    private String imageArtist;
    private String quote;
}
