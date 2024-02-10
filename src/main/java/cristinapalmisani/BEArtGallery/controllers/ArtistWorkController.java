package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;

import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkDTO;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkResponseDTO;
import cristinapalmisani.BEArtGallery.services.ArtistWorkService;
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
@RequestMapping("/artist-work")
public class ArtistWorkController {

    @Autowired
    private ArtistWorkService artistWorkService;

    @GetMapping
    public Page<ArtistWork> getArtistWork(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "uuid") String orderBy) {
        return artistWorkService.getArtistWork(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public ArtistWork getArtistWorkById(@PathVariable UUID uuid) {
        return artistWorkService.findById(uuid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ArtistWork getArtistWorkByIdAndUpdate(@PathVariable UUID id, @RequestBody ArtistWorkDTO artistBody) {
        return artistWorkService.findByIdAndUpdate(id, artistBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getArtistWorkByIdAndDelete(@PathVariable UUID id) {
        artistWorkService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ArtistWorkResponseDTO create(@RequestBody @Validated ArtistWorkDTO artistWork, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            ArtistWork newArtistWork = artistWorkService.save(artistWork);
            return new ArtistWorkResponseDTO(newArtistWork.getUuid());
        }
    }

    @PostMapping("/{uuid}/image")
    public String uploadExample(@PathVariable UUID uuid, @RequestParam("image") MultipartFile body) throws IOException {
        return artistWorkService.uploadPicture(uuid, body);
    }
}
