package cristinapalmisani.BEArtGallery.payloads.blog;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BlogDTO(@NotNull(message = "author work cannot be null")
                      @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                      String author,
                      @NotNull(message = "date work cannot be null")
                      LocalDate date,
                      @NotNull(message = "description cannot be null")
                      String description,
                      @NotNull(message = "title cannot be null")
                      @Size(min = 3, max = 60, message = "name must be between 3 e 60 chars")
                      String title
                      ) {
}
