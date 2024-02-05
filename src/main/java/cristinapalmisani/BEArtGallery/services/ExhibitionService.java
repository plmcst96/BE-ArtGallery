package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.Exhibition;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.blog.BlogDTO;
import cristinapalmisani.BEArtGallery.payloads.exhibition.ExhibitionDTO;
import cristinapalmisani.BEArtGallery.repositories.ExhibitionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExhibitionService {

    @Autowired
    private ExhibitionDAO exhibitionDAO;

    public Exhibition save(ExhibitionDTO body) {
        Exhibition exhibition = new Exhibition();
        exhibition.setEndDate(body.endDate());
        exhibition.setDescription(body.description());
        exhibition.setStartDate(body.startDate());
        exhibition.setTitle(body.title());
        exhibition.setOpenHour(body.openHour());
        exhibition.setAmount(body.amount());
        return exhibitionDAO.save(exhibition);
    }

    public Page<Exhibition> getExhibition(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return exhibitionDAO.findAll(pageable);
    }

    public Exhibition findById(UUID uuid) throws NotFoundException {
        return exhibitionDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        Exhibition artist = this.findById(id);
        exhibitionDAO.delete(artist);
    }

    public Exhibition findByIdAndUpdate(UUID id, ExhibitionDTO body) {
        Exhibition exhibition = this.findById(id);
        exhibition.setEndDate(body.endDate());
        exhibition.setDescription(body.description());
        exhibition.setStartDate(body.startDate());
        exhibition.setTitle(body.title());
        exhibition.setOpenHour(body.openHour());
        exhibition.setAmount(body.amount());
        return exhibitionDAO.save(exhibition);
    }
}
