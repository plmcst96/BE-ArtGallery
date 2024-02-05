package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.entities.Event;
import cristinapalmisani.BEArtGallery.entities.TypeEvent;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.payloads.event.EventDTO;
import cristinapalmisani.BEArtGallery.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    public Event save(EventDTO body) {
        Event event = new Event();
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setTypeEvent(TypeEvent.ONLINE);
        event.setAmount(body.amount());
        return eventDAO.save(event);
    }

    public Page<Event> getEvent(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventDAO.findAll(pageable);
    }

    public Event findById(UUID uuid) throws NotFoundException {
        return eventDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Event event = this.findById(id);
        eventDAO.delete(event);
    }

    public Event findByIdAndUpdate(UUID id, EventDTO body) {
        Event event = this.findById(id);
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setTypeEvent(TypeEvent.ONLINE);
        event.setAmount(body.amount());
        return eventDAO.save(event);
    }
}
