package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.artist.ArtistDTO;
import cristinapalmisani.BEArtGallery.payloads.user.UserDTO;
import cristinapalmisani.BEArtGallery.repositories.ArtistDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArtistService {

    @Autowired
    private ArtistDAO artistDAO;

    public Artist save(ArtistDTO body) {
        Artist artist = new Artist();
        artist.setName(body.name());
        artist.setSurname(body.surname());
        artist.setHistoryArtist(body.historyArtist());
        artist.setQuote(body.quote());
        artist.setDieDate(body.dieDate());
        artist.setBirthDate(body.birthDate());

        return artistDAO.save(artist);
    }

    public Page<Artist> getUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return artistDAO.findAll(pageable);
    }

    public Artist findById(UUID uuid) throws NotFoundException {
        return artistDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Artist artist = this.findById(id);
        artistDAO.delete(artist);
    }

    public Artist findByIdAndUpdate(UUID id, ArtistDTO body) {
        Artist artist = this.findById(id);
        artist.setName(body.name());
        artist.setSurname(body.surname());
        artist.setHistoryArtist(body.historyArtist());
        artist.setQuote(body.quote());
        artist.setDieDate(body.dieDate());
        artist.setBirthDate(body.birthDate());
        return artistDAO.save(artist);
    }
}
