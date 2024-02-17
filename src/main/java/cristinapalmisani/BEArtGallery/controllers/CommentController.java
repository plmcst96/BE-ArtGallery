package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentResponseDTO;
import cristinapalmisani.BEArtGallery.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public Page<Comment> getComment(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "uuid") String orderBy) {
        return commentService.getComment(page, size, orderBy);
    }

    @GetMapping("/{uuid}")
    public Comment getCommentById(@PathVariable UUID uuid) {
        return commentService.findById(uuid);
    }

    @GetMapping("/blog/{idBlog}")
    public Page<Comment> getCommentByBlog(@PathVariable UUID idBlog, @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "uuid") String orderBy){
        return commentService.findByIdBlog(idBlog, page, size, orderBy);
    }

    @PutMapping("/{uuid}")
    public Comment getCommentByIdAndUpdate(@PathVariable UUID uuid, @RequestBody CommentDTO commentBody) {
        return commentService.findByIdAndUpdate(uuid, commentBody);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getCommentByIdAndDelete(@PathVariable UUID uuid) {
        commentService.deleteById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDTO create(@RequestBody @Validated CommentDTO comment, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Comment newComment = commentService.save(comment);
            return new CommentResponseDTO(newComment.getUuid());
        }
    }
}
