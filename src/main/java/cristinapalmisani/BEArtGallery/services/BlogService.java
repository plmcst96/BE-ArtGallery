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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService {

    @Autowired
    private BlogDAO blogDAO;
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public Blog save(BlogDTO body) {
        Blog blog = new Blog();
        blog.setAuthor(body.author());
        blog.setDescription(body.description());
        blog.setDate(body.date());
        blog.setTitle(body.title());
        return blogDAO.save(blog);
    }

    @Transactional
    public Page<Blog> getBlog(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return blogDAO.findAll(pageable);
    }

    @Transactional
    public Blog findById(UUID uuid) throws NotFoundException {
        return blogDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    @Transactional
    public void deleteById(UUID id) {
        Blog blog = this.findById(id);
        blogDAO.delete(blog);
    }

    @Transactional
    public Blog findByIdAndUpdate(UUID id, BlogDTO body) {
        Blog blog = this.findById(id);
        blog.setAuthor(body.author());
        blog.setDescription(body.description());
        blog.setDate(body.date());
        blog.setTitle(body.title());
        return blogDAO.save(blog);
    }

    @Transactional
    public List<String> uploadPicture(UUID uuid, List<MultipartFile> files) throws IOException {
        Blog blog = blogDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            imageUrls.add(url);
        }

        // Aggiorna la lista delle immagini dell'evento
        blog.setImage(imageUrls);
        blogDAO.save(blog);

        return imageUrls;
    }
}
