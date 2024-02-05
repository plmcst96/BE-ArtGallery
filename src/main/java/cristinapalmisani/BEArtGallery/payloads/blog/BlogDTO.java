package cristinapalmisani.BEArtGallery.payloads.blog;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BlogDTO(@NotNull(message = "author work cannot be null")
                      @NotEmpty(message = "author work cannot be empty")
                      @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                      String author,
                      @NotNull(message = "date work cannot be null")
                      @NotEmpty(message = "date work cannot be empty")
                      LocalDate date,
                      @NotNull(message = "description cannot be null")
                      @NotEmpty(message = "description cannot be empty")
                      String description,
                      @NotNull(message = "title cannot be null")
                      @NotEmpty(message = "title cannot be empty")
                      @Size(min = 3, max = 30, message = "name must be between 3 e 30 chars")
                      String title
                      ) {
}
