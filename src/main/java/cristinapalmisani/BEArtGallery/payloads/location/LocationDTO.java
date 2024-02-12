package cristinapalmisani.BEArtGallery.payloads.location;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LocationDTO(@NotNull(message = "start date work cannot be null")
                          @NotEmpty(message = "start date work cannot be empty")
                          String address,
                          @NotNull(message = "start date work cannot be null")
                          @NotEmpty(message = "start date work cannot be empty")
                          String city,
                          @NotNull(message = "start date work cannot be null")
                          @NotEmpty(message = "start date work cannot be empty")
                          int zipCode,
                          @NotNull(message = "start date work cannot be null")
                          @NotEmpty(message = "start date work cannot be empty")
                          String nation,
                          @NotNull(message = "start date work cannot be null")
                          @NotEmpty(message = "start date work cannot be empty")
                          String nameMuseum) {
}
