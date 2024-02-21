package cristinapalmisani.BEArtGallery.payloads.location;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LocationDTO(@NotNull(message = "start date work cannot be null")
                          String address,
                          @NotNull(message = "start date work cannot be null")
                          String city,
                          @NotNull(message = "start date work cannot be null")
                          int zipCode,
                          @NotNull(message = "start date work cannot be null")
                          String nation,
                          @NotNull(message = "start date work cannot be null")
                          String nameMuseum,
                          UUID artistWork,
                          UUID exhibition) {
}
