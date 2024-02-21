package cristinapalmisani.BEArtGallery.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExhibitionService {

    @Autowired
    private ExhibitionDAO exhibitionDAO;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ArtistWorkService artistWorkService;

    @Transactional
    public Exhibition save(ExhibitionDTO body) {
        Exhibition exhibition = new Exhibition();
        ArtistWork artistWork = artistWorkService.findById(body.artistWork());
        exhibition.setEndDate(body.endDate());
        exhibition.setDescription(body.description());
        exhibition.setStartDate(body.startDate());
        exhibition.setTitle(body.title());
        exhibition.setOpenHour(body.openHour());
        exhibition.setAmount(body.amount());
        exhibition.setArtistWork(artistWork);
        return exhibitionDAO.save(exhibition);
    }

    @Transactional
    public Page<Exhibition> getExhibition(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return exhibitionDAO.findAll(pageable);
    }

    @Transactional
    public Exhibition findById(UUID uuid) throws NotFoundException {
        return exhibitionDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    @Transactional
    public void deleteById(UUID id) {
        Exhibition artist = this.findById(id);
        exhibitionDAO.delete(artist);
    }

    @Transactional
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

    @Transactional
    public List<String> uploadPicture(UUID uuid, List<MultipartFile> files) throws IOException {
        Exhibition exhibition = exhibitionDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            imageUrls.add(url);
        }

        // Aggiorna la lista delle immagini dell'evento
        exhibition.setImage(imageUrls);
        exhibitionDAO.save(exhibition);

        return imageUrls;
    }
}
