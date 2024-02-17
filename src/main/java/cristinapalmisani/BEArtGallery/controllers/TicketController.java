package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.entities.Ticket;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.ticket.TicketDTO;
import cristinapalmisani.BEArtGallery.payloads.ticket.TicketResponseDTO;
import cristinapalmisani.BEArtGallery.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public Page<Ticket> getTicket(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "uuid") String orderBy) {
        return ticketService.getTicket(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable UUID uuid) {
        return ticketService.findById(uuid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Ticket getTicketByIdAndUpdate(@PathVariable UUID id, @RequestBody TicketDTO ticketBody) {
        return ticketService.findByIdAndUpdate(id, ticketBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getTicketByIdAndDelete(@PathVariable UUID id) {
        ticketService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public TicketResponseDTO create(@RequestBody @Validated TicketDTO comment, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Ticket newTicket = ticketService.save(comment);
            return new TicketResponseDTO(newTicket.getUuid());
        }
    }
}
