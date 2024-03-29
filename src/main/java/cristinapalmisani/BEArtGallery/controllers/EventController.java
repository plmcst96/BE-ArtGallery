package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.entities.Event;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.event.EventDTO;
import cristinapalmisani.BEArtGallery.payloads.event.EventResponseDTO;
import cristinapalmisani.BEArtGallery.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public Page<Event> getEvent(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "uuid") String orderBy) {
        return eventService.getEvent(page, size, orderBy);
    }

    @GetMapping("/{uuid}")
    public Event getEventById(@PathVariable UUID uuid) {
        return eventService.findById(uuid);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event getEventByIdAndUpdate(@PathVariable UUID uuid, @RequestBody EventDTO eventBody) {
        return eventService.findByIdAndUpdate(uuid, eventBody);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getEventByIdAndDelete(@PathVariable UUID uuid) {
        eventService.deleteById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventResponseDTO create(@RequestBody @Validated EventDTO event, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Event newEvent = eventService.save(event);
            return new EventResponseDTO(newEvent.getUuid());
        }
    }
    @PostMapping("/{uuid}/image")
    public List<String> uploadExample(@PathVariable UUID uuid, @RequestParam("image") List<MultipartFile> body) throws IOException {
        return eventService.uploadPicture(uuid, body);
    }
}
