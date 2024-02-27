package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
public class Event extends GeneralAttribute{
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private LocalDate date;
    private TypeEvent typeEvent;
    private double amount;
    @Column(name = "max_num", nullable = false, columnDefinition = "bigint default 0")
    private Long maxNum;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Ticket> tickets;


    public Event(String title, String description, LocalDate date, TypeEvent typeEvent, double amount) {
        super(title, description);
        this.date = date;
        this.typeEvent = typeEvent;
        this.amount = amount;
    }

    public Event() {
        this.tickets = new ArrayList<>();
    }


}
