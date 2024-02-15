package cristinapalmisani.BEArtGallery.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private double total;
    private String date;
    @Column(name = "image_PDF")
    private String imageDoc;

    private String hour;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ticket")
    private TypeTicket typeTicket;
    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
