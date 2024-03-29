package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.entities.Exhibition;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.exhibition.ExhibitionDTO;
import cristinapalmisani.BEArtGallery.payloads.exhibition.ExhibitionResponseDTO;
import cristinapalmisani.BEArtGallery.services.ExhibitionService;
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
@RequestMapping("/exhibitions")
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @GetMapping("/all")
    public Page<Exhibition> getExhibition(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "uuid") String orderBy) {
        return exhibitionService.getExhibition(page, size, orderBy);
    }

    @GetMapping("/{uuid}")
    public Exhibition getExhibitionById(@PathVariable UUID uuid) {
        return exhibitionService.findById(uuid);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Exhibition getExhibitionByIdAndUpdate(@PathVariable UUID uuid, @RequestBody ExhibitionDTO exhibitionBody) {
        return exhibitionService.findByIdAndUpdate(uuid, exhibitionBody);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getExhibitionByIdAndDelete(@PathVariable UUID uuid) {
        exhibitionService.deleteById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ExhibitionResponseDTO create(@RequestBody @Validated ExhibitionDTO exhibition, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Exhibition newExhibition = exhibitionService.save(exhibition);
            return new ExhibitionResponseDTO(newExhibition.getUuid());
        }
    }

    @PostMapping("/{uuid}/image")
    public List<String> uploadExample(@PathVariable UUID uuid, @RequestParam("image") List<MultipartFile> body) throws IOException {
        return exhibitionService.uploadPicture(uuid, body);
    }
}
