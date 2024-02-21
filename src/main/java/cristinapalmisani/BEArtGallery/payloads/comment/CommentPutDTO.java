package cristinapalmisani.BEArtGallery.payloads.comment;

import jakarta.validation.constraints.NotNull;

public record CommentPutDTO(@NotNull(message = "Rate work cannot be null")
                            int rate,
                            @NotNull(message = "Text work cannot be null")
                            String text) {
}
