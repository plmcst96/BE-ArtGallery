package cristinapalmisani.BEArtGallery.payloads.ticket;

import cristinapalmisani.BEArtGallery.entities.TypeTicket;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record TicketDTO(@NotNull(message = "date work cannot be null")
                        String date,

                        String hour,
                        @NotNull(message = "start date work cannot be null")
                        double total


                        ) {
}
