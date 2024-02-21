package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.entities.Location;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.location.LocationDTO;
import cristinapalmisani.BEArtGallery.payloads.location.LocationResponseDTO;
import cristinapalmisani.BEArtGallery.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public Page<Location> getLocation(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "uuid") String orderBy) {
        return locationService.getLocation(page, size, orderBy);
    }

    @GetMapping("/{uuid}")
    public Location getLocationById(@PathVariable UUID uuid) {
        return locationService.findById(uuid);
    }

    @GetMapping("/exhibition/{uuid}")
    public Location getLocationByExhibitionId(@PathVariable UUID uuid){
        return locationService.findByExhibitionId(uuid);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Location getLocationByIdAndUpdate(@PathVariable UUID uuid, @RequestBody LocationDTO locationBody) {
        return locationService.findByIdAndUpdate(uuid, locationBody);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getLocationByIdAndDelete(@PathVariable UUID uuid) {
        locationService.deleteById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public LocationResponseDTO create(@RequestBody @Validated LocationDTO location, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Location newLocation = locationService.save(location);
            return new LocationResponseDTO(newLocation.getUuid());
        }
    }
}
