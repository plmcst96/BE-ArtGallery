package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.Gallery;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.gallery.GalleryDTO;
import cristinapalmisani.BEArtGallery.payloads.gallery.GalleryResponseDTO;
import cristinapalmisani.BEArtGallery.repositories.ArtistDAO;
import cristinapalmisani.BEArtGallery.repositories.GalleryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GalleryService {
    @Autowired
    private GalleryDAO galleryDAO;
    @Autowired
    private ArtistDAO artistDAO;

    public Gallery save(GalleryDTO artistId) {
        Gallery gallery = new Gallery();
        Artist artist = artistDAO.findById(artistId.artistUuid()).orElseThrow(()-> new NotFoundException("Id not found"));
        gallery.setArtist(artist);
        return galleryDAO.save(gallery);
    }

    public Page<Gallery> getGallery(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return galleryDAO.findAll(pageable);
    }

    public Gallery findById(UUID uuid) throws NotFoundException {
        return galleryDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID uuid) {
        Gallery gallery = this.findById(uuid);
        galleryDAO.delete(gallery);
    }

    public UUID getGalleryIdByArtistId(UUID artistId) {
        Artist artist = artistDAO.findById(artistId)
                .orElseThrow(() -> new NotFoundException("Artista non trovato con ID: " + artistId));

        if (artist.getGallery() != null) {
            return artist.getGallery().getUuid();
        } else {
            throw new NotFoundException("Galleria non trovata per l'artista con ID: " + artistId);
        }
    }

}
