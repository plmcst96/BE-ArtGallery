package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.repositories.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    public Comment save(CommentDTO body) {
        Comment comment = new Comment();
        comment.setText(body.text());
        comment.setRate(body.rate());

        return commentDAO.save(comment);
    }

    public Page<Comment> getComment(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return commentDAO.findAll(pageable);
    }

    public Comment findById(UUID uuid) throws NotFoundException {
        return commentDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Comment artist = this.findById(id);
        commentDAO.delete(artist);
    }

    public Comment findByIdAndUpdate(UUID id, CommentDTO body) {
        Comment comment = this.findById(id);
        comment.setText(body.text());
        comment.setRate(body.rate());
        return commentDAO.save(comment);
    }
}
