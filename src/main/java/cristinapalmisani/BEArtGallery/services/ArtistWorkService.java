package cristinapalmisani.BEArtGallery.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.ArtistWork;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.artist.ArtistDTO;
import cristinapalmisani.BEArtGallery.payloads.artistWork.ArtistWorkDTO;
import cristinapalmisani.BEArtGallery.repositories.ArtistWorkDAO;
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
public class ArtistWorkService {

    @Autowired
    private ArtistWorkDAO artistWorkDAO;
    @Autowired
    private Cloudinary cloudinary;

    public ArtistWork save(ArtistWorkDTO body) {
        ArtistWork artistWork = new ArtistWork();
        artistWork.setNameWork(body.nameWork());
        artistWork.setDescription(body.description());
        artistWork.setDateStartWork(body.dateStartWork());
        artistWork.setTechnique(body.technique());

        return artistWorkDAO.save(artistWork);
    }

    public Page<ArtistWork> getArtistWork(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return artistWorkDAO.findAll(pageable);
    }

    public ArtistWork findById(UUID uuid) throws NotFoundException {
        return artistWorkDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }


    public void deleteById(UUID id) {
        ArtistWork artist = this.findById(id);
        artistWorkDAO.delete(artist);
    }

    public ArtistWork findByIdAndUpdate(UUID id, ArtistWorkDTO body) {
        ArtistWork artistWork = this.findById(id);
        artistWork.setNameWork(body.nameWork());
        artistWork.setDescription(body.description());
        artistWork.setDateStartWork(body.dateStartWork());
        artistWork.setTechnique(body.technique());
        return artistWorkDAO.save(artistWork);
    }

    public String uploadPicture(UUID uuid, MultipartFile file) throws IOException {
        ArtistWork work = artistWorkDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        work.setImage(url);
        artistWorkDAO.save(work);
        return url;
    }
}
