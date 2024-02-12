package cristinapalmisani.BEArtGallery.payloads.ticket;

import cristinapalmisani.BEArtGallery.entities.TypeTicket;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TicketDTO(@NotNull(message = "date work cannot be null")
                        @NotEmpty(message = "date work cannot be empty")
                        LocalDate date,
                        @NotNull(message = "start date work cannot be null")
                        @NotEmpty(message = "start date work cannot be empty")
                        int numberPerson,
                        @NotNull(message = "start date work cannot be null")
                        @NotEmpty(message = "start date work cannot be empty")
                        double total,
                        @NotNull(message = "start date work cannot be null")
                        @NotEmpty(message = "start date work cannot be empty")
                        TypeTicket typeTicket

                        ) {
}
