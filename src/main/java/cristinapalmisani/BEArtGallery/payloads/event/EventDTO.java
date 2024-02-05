package cristinapalmisani.BEArtGallery.payloads.event;

import cristinapalmisani.BEArtGallery.entities.TypeEvent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventDTO(@NotNull(message = "date work cannot be null")
                       @NotEmpty(message = "date work cannot be empty")
                       LocalDate date,
                       @NotNull(message = "description cannot be null")
                       @NotEmpty(message = "description cannot be empty")
                       String description,
                       @NotNull(message = "title cannot be null")
                       @NotEmpty(message = "title cannot be empty")
                       @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                       String title,
                       @NotNull(message = "type event cannot be null")
                       @NotEmpty(message = "type event cannot be empty")
                       TypeEvent typeEvent,
                       @NotNull(message = "type event cannot be null")
                       @NotEmpty(message = "type event cannot be empty")
                       double amount) {
}
