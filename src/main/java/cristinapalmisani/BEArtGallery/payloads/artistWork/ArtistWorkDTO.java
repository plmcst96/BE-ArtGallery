package cristinapalmisani.BEArtGallery.payloads.artistWork;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ArtistWorkDTO(@NotNull(message = "name work cannot be null")
                            @NotEmpty(message = "name work cannot be empty")
                            @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                            String nameWork,
                            @NotNull(message = "technique cannot be null")
                            @NotEmpty(message = "technique cannot be empty")
                            @Size(min = 3, max = 30, message = "surname must be between 3 e 30 chars")
                            String technique,
                            @NotNull(message = "surname cannot be null")
                            @NotEmpty(message = "surname cannot be empty")
                            LocalDate dateStartWork,
                            @NotNull(message = "surname cannot be null")
                            @NotEmpty(message = "surname cannot be empty")
                            String description
                            ) {
}
