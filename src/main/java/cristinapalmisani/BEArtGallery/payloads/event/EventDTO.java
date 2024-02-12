package cristinapalmisani.BEArtGallery.payloads.event;

import cristinapalmisani.BEArtGallery.entities.TypeEvent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventDTO(@NotNull(message = "date work cannot be null")

                       LocalDate date,
                       @NotNull(message = "description cannot be null")

                       String description,
                       @NotNull(message = "title cannot be null")

                       @Size(min = 3, max = 60, message = "name must be between 3 e 60 chars")
                       String title,
                       @NotNull(message = "type event cannot be null")

                       TypeEvent typeEvent,

                       double amount) {
}
