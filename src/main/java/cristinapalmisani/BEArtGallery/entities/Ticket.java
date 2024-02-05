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
    private LocalDate date;
    @Column(name = "image_PDF")
    private String imageDoc;
    @Column(name = "number_people")
    private int numberPerson;
    private double amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ticket")
    private TypeTicket typeTicket;

}
