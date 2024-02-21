package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.entities.Blog;
import cristinapalmisani.BEArtGallery.entities.Exhibition;
import cristinapalmisani.BEArtGallery.entities.Location;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.payloads.location.LocationDTO;
import cristinapalmisani.BEArtGallery.repositories.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationDAO locationDAO;
    @Autowired
    private ArtistWorkService artistWorkService;
    @Autowired
    private ExhibitionService exhibitionService;

    @Transactional
    public Location save(LocationDTO body) {
        Location location = new Location();
        ArtistWork artistWork = artistWorkService.findById(body.artistWork());
        Exhibition exhibition = exhibitionService.findById(body.exhibition());
        location.setAddress(body.address());
        location.setCity(body.city());
        location.setNation(body.nation());
        location.setZipCode(body.zipCode());
        location.setMuseumName(body.nameMuseum());
        location.setArtistWork(artistWork);
        location.setExhibition(exhibition);
        return locationDAO.save(location);
    }

    @Transactional
    public Page<Location> getLocation(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return locationDAO.findAll(pageable);
    }

    @Transactional
    public Location findById(UUID uuid) throws NotFoundException {
        return locationDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    @Transactional
    public Location findByExhibitionId(UUID exhibitionId) throws NotFoundException{
        return locationDAO.findByExhibitionUuid(exhibitionId).orElseThrow(()-> new NotFoundException(exhibitionId));
    }


    @Transactional
    public void deleteById(UUID id) {
        Location location = this.findById(id);
        locationDAO.delete(location);
    }

    @Transactional
    public Location findByIdAndUpdate(UUID id, LocationDTO body) {
        Location location = this.findById(id);
        location.setAddress(body.address());
        location.setCity(body.city());
        location.setNation(body.nation());
        location.setZipCode(body.zipCode());
        location.setMuseumName(body.nameMuseum());
        return locationDAO.save(location);
    }
}
