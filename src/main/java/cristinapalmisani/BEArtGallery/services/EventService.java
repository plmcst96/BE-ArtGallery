package cristinapalmisani.BEArtGallery.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEArtGallery.entities.Artist;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public Event save(EventDTO body) {
        Event event = new Event();
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setTypeEvent(TypeEvent.ONLINE);
        event.setAmount(body.amount());
        return eventDAO.save(event);
    }

    @Transactional
    public Page<Event> getEvent(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventDAO.findAll(pageable);
    }

    @Transactional
    public Event findById(UUID uuid) throws NotFoundException {
        return eventDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    @Transactional
    public void deleteById(UUID id) {
        Event event = this.findById(id);
        eventDAO.delete(event);
    }
    @Transactional
    public Event findByIdAndUpdate(UUID id, EventDTO body) {
        Event event = this.findById(id);
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setTypeEvent(TypeEvent.ONLINE);
        event.setAmount(body.amount());
        return eventDAO.save(event);
    }

    @Transactional
    public List<String> uploadPicture(UUID uuid, List<MultipartFile> files) throws IOException {
        Event event = eventDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            imageUrls.add(url);
        }
        if (event.getImage() == null) {
            event.setImage(new ArrayList<>());
        }

        // Aggiorna la lista delle immagini dell'evento
        event.getImage().addAll(imageUrls);
        eventDAO.save(event);

        return imageUrls;
    }
}
