package cristinapalmisani.BEArtGallery.payloads.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentDTO(@NotNull(message = "DateTime work cannot be null")
                         @NotEmpty(message = "DateTime work cannot be empty")
                         LocalDateTime dateTime,
                         @NotNull(message = "text work cannot be null")
                         @NotEmpty(message = "text work cannot be empty")
                         String text) {
}
