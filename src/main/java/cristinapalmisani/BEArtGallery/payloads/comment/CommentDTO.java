package cristinapalmisani.BEArtGallery.payloads.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentDTO(@NotNull(message = "Rate work cannot be null")
                         int rate,
                         @NotNull(message = "Text work cannot be null")
                         String text) {
}
