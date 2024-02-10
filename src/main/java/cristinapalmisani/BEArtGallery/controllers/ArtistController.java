package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.artist.ArtistDTO;
import cristinapalmisani.BEArtGallery.payloads.artist.ArtistResponseDTO;
import cristinapalmisani.BEArtGallery.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @GetMapping
    public Page<Artist> getArtist(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "uuid") String orderBy) {
        return artistService.getArtist(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable UUID uuid) {
        return artistService.findById(uuid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Artist getArtistByIdAndUpdate(@PathVariable UUID id, @RequestBody ArtistDTO artistBody) {
        return artistService.findByIdAndUpdate(id, artistBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getArtistByIdAndDelete(@PathVariable UUID id) {
        artistService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ArtistResponseDTO create(@RequestBody @Validated ArtistDTO artist, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Artist newArtist = artistService.save(artist);
            return new ArtistResponseDTO(newArtist.getUuid());
        }
    }
    @PostMapping("/{uuid}/image")
    public String uploadExample(@PathVariable UUID uuid, @RequestParam("image") MultipartFile body) throws IOException {
        return artistService.uploadPicture(uuid, body);
    }
}
