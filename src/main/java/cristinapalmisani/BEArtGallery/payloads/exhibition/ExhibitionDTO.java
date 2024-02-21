package cristinapalmisani.BEArtGallery.payloads.exhibition;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.UUID;

public record ExhibitionDTO(@NotNull(message = "start date work cannot be null")
                            LocalDate startDate,
                            @NotNull(message = "description cannot be null")
                            String description,
                            @NotNull(message = "title cannot be null")
                            @Size(min = 3, max = 70, message = "name must be between 3 e 70 chars")
                            String title,
                            @NotNull(message = "end date work cannot be null")
                            LocalDate endDate,
                            @NotNull(message = "open hour work cannot be null")
                            String openHour,
                            @NotNull(message = "type event cannot be null")
                            double amount,
                            UUID artistWork) {
}
