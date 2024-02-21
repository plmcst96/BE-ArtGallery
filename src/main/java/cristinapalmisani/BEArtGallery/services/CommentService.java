package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.entities.Comment;
import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentDTO;
import cristinapalmisani.BEArtGallery.payloads.comment.CommentPutDTO;
import cristinapalmisani.BEArtGallery.repositories.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @Transactional
    public Comment save(CommentDTO body) {
        Comment comment = new Comment();
        Blog blog = blogService.findById(body.blog());
        User user = userService.findById(body.user());
        comment.setText(body.text());
        comment.setRate(body.rate());
        comment.setBlog(blog);
        comment.setUser(user);
        return commentDAO.save(comment);
    }

    @Transactional
    public Page<Comment> getComment(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return commentDAO.findAll(pageable);
    }
    @Transactional
    public Comment findById(UUID uuid) throws NotFoundException {
        return commentDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    @Transactional
    public Page<Comment> findByIdBlog(UUID blogUuid, int page, int size, String sort) throws  NotFoundException{
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return commentDAO.findByBlogUuid(blogUuid, pageable );
    }


    @Transactional
    public void deleteById(UUID uuid) {
        Comment comment = this.findById(uuid);
        commentDAO.delete(comment);
    }


    public Comment findByIdAndUpdate(UUID uuid, CommentPutDTO body) {
        Comment comment = this.findById(uuid);
        comment.setText(body.text());
        comment.setRate(body.rate());
        return commentDAO.save(comment);
    }
}
