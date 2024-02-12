package cristinapalmisani.BEArtGallery.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.entities.Gallery;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.artist.ArtistDTO;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkDTO;
import cristinapalmisani.BEArtGallery.repositories.ArtistWorkDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ArtistWorkService {

    @Autowired
    private ArtistWorkDAO artistWorkDAO;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private GalleryService galleryService;
    @Autowired
    private ArtistService artistService;


    public ArtistWork save(ArtistWorkDTO body) {
        ArtistWork artistWork = new ArtistWork();
        Gallery gallery = galleryService.findById(body.galleryId().getUuid());
        if (gallery == null) {
            throw new NotFoundException("Gallery with ID " + body.galleryId().getUuid() + " not found");
        }
        artistWork.setNameWork(body.nameWork());
        artistWork.setDescription(body.description());
        artistWork.setYearStartWork(body.yearStartWork());
        artistWork.setTechnique(body.technique());
        artistWork.setGallery(gallery);
        return artistWorkDAO.save(artistWork);
    }

    public Page<ArtistWork> getArtistWork(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return artistWorkDAO.findAll(pageable);
    }

    public ArtistWork findById(UUID uuid) throws NotFoundException {
        return artistWorkDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID uuid) {
        ArtistWork artist = this.findById(uuid);
        artistWorkDAO.delete(artist);
    }

    public ArtistWork findByIdAndUpdate(UUID uuid, ArtistWorkDTO body) {
        ArtistWork artistWork = this.findById(uuid);
        artistWork.setNameWork(body.nameWork());
        artistWork.setDescription(body.description());
        artistWork.setYearStartWork(body.yearStartWork());
        artistWork.setTechnique(body.technique());
        return artistWorkDAO.save(artistWork);
    }

    public Page<ArtistWork> getArtworksByArtistId(UUID artistId, int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        Artist artist = artistService.findById(artistId);
        return artistWorkDAO.findByGalleryArtistUuid(artistId, pageable);
    }

    public Page<ArtistWork> getArtworksByYear(long year, int page, int size, String ordedBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(ordedBy));

        return artistWorkDAO.findByYearStartWork(year, pageable );
    }

    public String uploadPicture(UUID uuid, MultipartFile file) throws IOException {
        ArtistWork work = artistWorkDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        work.setImage(url);
        artistWorkDAO.save(work);
        return url;
    }
}
