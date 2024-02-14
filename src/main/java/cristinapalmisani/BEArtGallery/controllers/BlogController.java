package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkDTO;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogResponseDTO;
import cristinapalmisani.BEArtGallery.services.BlogService;
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
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public Page<Blog> getBlog(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "uuid") String orderBy) {
        return blogService.getBlog(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable UUID uuid) {
        return blogService.findById(uuid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Blog getBlogByIdAndUpdate(@PathVariable UUID id, @RequestBody BlogDTO artistBody) {
        return blogService.findByIdAndUpdate(id, artistBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getBlogByIdAndDelete(@PathVariable UUID id) {
        blogService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public BlogResponseDTO create(@RequestBody @Validated BlogDTO blog, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Blog newBlog = blogService.save(blog);
            return new BlogResponseDTO(newBlog.getUuid());
        }
    }

    @PostMapping("/{uuid}/image")
    public List<String> uploadExample(@PathVariable UUID uuid, @RequestParam("image") List<MultipartFile> body) throws IOException {
        return blogService.uploadPicture(uuid, body);
    }
}
