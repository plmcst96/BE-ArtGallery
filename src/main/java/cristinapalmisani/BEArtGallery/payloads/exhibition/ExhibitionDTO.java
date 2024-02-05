package cristinapalmisani.BEArtGallery.payloads.exhibition;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import java.time.LocalTime;

public record ExhibitionDTO(@NotNull(message = "start date work cannot be null")
                            @NotEmpty(message = "start date work cannot be empty")
                            LocalDate startDate,
                            @NotNull(message = "description cannot be null")
                            @NotEmpty(message = "description cannot be empty")
                            String description,
                            @NotNull(message = "title cannot be null")
                            @NotEmpty(message = "title cannot be empty")
                            @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                            String title,
                            @NotNull(message = "end date work cannot be null")
                            @NotEmpty(message = "end date work cannot be empty")
                            LocalDate endDate,
                            @NotNull(message = "open hour work cannot be null")
                            @NotEmpty(message = "open hour work cannot be empty")
                            LocalTime openHour,
                            @NotNull(message = "type event cannot be null")
                            @NotEmpty(message = "type event cannot be empty")
                            double amount) {
}
