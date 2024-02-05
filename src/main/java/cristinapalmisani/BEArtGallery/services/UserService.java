package cristinapalmisani.BEArtGallery.services;

import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.exception.NotFoundException;
import cristinapalmisani.BEArtGallery.payloads.user.UserDTO;
import cristinapalmisani.BEArtGallery.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;


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
}
