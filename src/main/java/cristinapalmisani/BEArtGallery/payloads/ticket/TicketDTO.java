package cristinapalmisani.BEArtGallery.payloads.ticket;

import cristinapalmisani.BEArtGallery.entities.TypeTicket;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TicketDTO(@NotNull(message = "date work cannot be null")
                        String date,
                        UUID event,
                        String hour,
                        @NotNull(message = "start date work cannot be null")
                        double total


                        ) {
}
