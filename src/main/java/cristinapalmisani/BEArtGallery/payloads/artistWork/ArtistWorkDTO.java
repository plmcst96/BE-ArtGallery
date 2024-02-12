package cristinapalmisani.BEArtGallery.payloads.artistWork;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.Gallery;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record ArtistWorkDTO(@NotNull(message = "name work cannot be null")
                            @Size(min = 3, max = 60, message = "name must be between 3 e 60 chars")
                            String nameWork,
                            @NotNull(message = "technique cannot be null")
                            @Size(min = 3, max = 80, message = "surname must be between 3 e 80 chars")
                            String technique,
                            @NotNull(message = "surname cannot be null")
                            long yearStartWork,
                            @NotNull(message = "surname cannot be null")
                            String description,
                            Gallery galleryId,
                            Artist artistId
                            ) {
}
