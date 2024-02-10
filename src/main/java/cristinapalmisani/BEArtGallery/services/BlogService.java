package cristinapalmisani.BEArtGallery.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkDTO;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.repositories.BlogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class BlogService {

    @Autowired
    private BlogDAO blogDAO;
    @Autowired
    private Cloudinary cloudinary;

    public Blog save(BlogDTO body) {
        Blog blog = new Blog();
        blog.setAuthor(body.author());
        blog.setDescription(body.description());
        blog.setDate(body.date());
        blog.setTitle(body.title());
        return blogDAO.save(blog);
    }

    public Page<Blog> getBlog(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return blogDAO.findAll(pageable);
    }

    public Blog findById(UUID uuid) throws NotFoundException {
        return blogDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Blog blog = this.findById(id);
        blogDAO.delete(blog);
    }

    public Blog findByIdAndUpdate(UUID id, BlogDTO body) {
        Blog blog = this.findById(id);
        blog.setAuthor(body.author());
        blog.setDescription(body.description());
        blog.setDate(body.date());
        blog.setTitle(body.title());
        return blogDAO.save(blog);
    }

    public String uploadPicture(UUID uuid, MultipartFile file) throws IOException {
        Blog blog = blogDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        blog.setImage(url);
        blogDAO.save(blog);
        return url;
    }
}
