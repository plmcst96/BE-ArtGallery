package cristinapalmisani.BEArtGallery.controllers;


import cristinapalmisani.BEArtGallery.entities.Gallery;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;

import cristinapalmisani.BEArtGallery.payloads.gallery.GalleryDTO;
import cristinapalmisani.BEArtGallery.payloads.gallery.GalleryResponseDTO;
import cristinapalmisani.BEArtGallery.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/gallery")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public Page<Gallery> getGalleries(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "uuid") String orderBy) {
        return galleryService.getGallery(page, size, orderBy);
    }

    @GetMapping("/{uuid}")
    public Gallery getGalleryById(@PathVariable UUID uuid) {
        return galleryService.findById(uuid);
    }


    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getGalleryByIdAndDelete(@PathVariable UUID uuid) {
        galleryService.deleteById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAuthority('ADMIN')")
    public GalleryResponseDTO create(@RequestBody GalleryDTO artistUuid, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Gallery gallery = galleryService.save(artistUuid);
            return new GalleryResponseDTO(gallery.getUuid());
        }
    }

    @GetMapping("/artist/{artistId}")
    public GalleryResponseDTO getGalleryIdByArtistId(@PathVariable UUID artistId) {
        UUID galleryId = galleryService.getGalleryIdByArtistId(artistId);
        return new GalleryResponseDTO(galleryId);
    }
}
