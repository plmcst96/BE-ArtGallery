package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.entities.Gallery;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkDTO;
import cristinapalmisani.BEArtGallery.payloads.gallery.GalleryDTO;
import cristinapalmisani.BEArtGallery.repositories.GalleryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GalleryService {
    @Autowired
    private GalleryDAO galleryDAO;
    @Autowired
    private ArtistService artistService;

    public Gallery save(GalleryDTO body) {
        Gallery gallery = new Gallery();
        Artist artist = artistService.findById(body.artistId());
        gallery.setArtist(artist.getUuid());
        return galleryDAO.save(gallery);
    }

    public Page<Gallery> getGallery(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return galleryDAO.findAll(pageable);
    }

    public Gallery findById(UUID uuid) throws NotFoundException {
        return galleryDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Gallery gallery = this.findById(id);
        galleryDAO.delete(gallery);
    }

}
