package cristinapalmisani.BEArtGallery.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import cristinapalmisani.BEArtGallery.entities.Artist;
import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.formCurator.FormDataCuratorDTO;
import cristinapalmisani.BEArtGallery.payloads.user.UserDTO;
import cristinapalmisani.BEArtGallery.repositories.UserDAO;
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
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Cloudinary cloudinary;


    public Page<User> getUsers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userDAO.findAll(pageable);
    }

    public User findById(UUID uuid) throws NotFoundException {
        return userDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));
    }

    public void deleteById(UUID id) {
        User user = this.findById(id);
        userDAO.delete(user);
    }

    public User findByIdAndUpdate(UUID id, UserDTO body) {
        User user = this.findById(id);
        user.setEmail(body.email());
        user.setName(body.name());
        user.setSurname(body.surname());
        return userDAO.save(user);
    }

    public User setAccepted(String email){
        User curator = userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException("Invalid email"));
        curator.setAccepted(true);
        return userDAO.save(curator);
    }
    public String uploadPicture(UUID uuid, MultipartFile file) throws IOException {
        User user = userDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        user.setAvatar(url);
        userDAO.save(user);
        return url;
    }
}
