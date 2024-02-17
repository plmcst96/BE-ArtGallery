package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.*;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.location.LocationDTO;
import cristinapalmisani.BEArtGallery.payloads.ticket.TicketDTO;
import cristinapalmisani.BEArtGallery.repositories.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    public Ticket save(TicketDTO body) {
        Ticket ticket = new Ticket();
        Event event = eventService.findById(body.event());
        User user = userService.findById(body.user());
        ticket.setDate(body.date());
        ticket.setHour(body.hour());
        ticket.setEvent(event);
        ticket.setTotal(body.total());
        ticket.setUser(user);
        return ticketDAO.save(ticket);
    }

    public Page<Ticket> getTicket(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ticketDAO.findAll(pageable);
    }

    public Ticket findById(UUID uuid) throws NotFoundException {
        return ticketDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Ticket ticket = this.findById(id);
        ticketDAO.delete(ticket);
    }

    public Ticket findByIdAndUpdate(UUID id, TicketDTO body) {
        Ticket ticket = this.findById(id);
        ticket.setDate(body.date());
        ticket.setHour(body.hour());
        ticket.setTotal(body.total());
        return ticketDAO.save(ticket);
    }
}
