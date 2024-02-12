package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Blog;
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

import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationDAO locationDAO;

    public Location save(LocationDTO body) {
        Location location = new Location();
        location.setAddress(body.address());
        location.setCity(body.city());
        location.setNation(body.nation());
        location.setZipCode(body.zipCode());
        location.setMuseumName(body.nameMuseum());
        return locationDAO.save(location);
    }

    public Page<Location> getLocation(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return locationDAO.findAll(pageable);
    }

    public Location findById(UUID uuid) throws NotFoundException {
        return locationDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Location location = this.findById(id);
        locationDAO.delete(location);
    }

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
