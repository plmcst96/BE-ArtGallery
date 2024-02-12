package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "exhibitions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Exhibition extends GeneralAttribute{
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "open_hours")
    private LocalTime openHour;
    private double amount;

    @OneToOne(mappedBy = "exhibition")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "artist_work_id")
    private ArtistWork artistWork;
    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Ticket> tickets;

}
