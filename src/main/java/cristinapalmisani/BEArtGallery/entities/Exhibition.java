package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "exhibitions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Exhibition {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String title;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    private String description;
    private String image;
    @Column(name = "open_hours")
    private LocalTime openHour;

}
