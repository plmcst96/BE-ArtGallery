package cristinapalmisani.BEArtGallery.payloads.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ArtistDTO(@NotNull(message = "name cannot be null")
                        @NotEmpty(message = "name cannot be empty")
                        @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                        String name,
                        @NotNull(message = "surname cannot be null")
                        @NotEmpty(message = "surname cannot be empty")
                        @Size(min = 3, max = 30, message = "surname must be between 3 e 30 chars")
                        String surname,
                        @NotNull(message = "birth date cannot be null")
                        LocalDate birthDate,
                        LocalDate dieDate,

                        @NotNull(message = "History artist cannot be null")
                        @NotEmpty(message = "History artist cannot be empty")
                        String historyArtist,
                        @NotNull(message = "quote cannot be null")
                        @NotEmpty(message = "quote cannot be empty")
                        String quote) {
}
