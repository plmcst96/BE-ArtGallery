package cristinapalmisani.BEArtGallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "blogs")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Blog extends GeneralAttribute{
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    private String author;
    private String quote;
    private LocalDate date;
    @OneToMany(mappedBy = "blog")
    @ToString.Exclude
    @JsonIgnore
    private List<Comment> comments;


}
