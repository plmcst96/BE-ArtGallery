package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.config.EmailSender;
import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.payloads.formCurator.FormDataCuratorDTO;
import cristinapalmisani.BEArtGallery.payloads.formCurator.FormDataResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.user.UserDTO;
import cristinapalmisani.BEArtGallery.services.AuthService;
import cristinapalmisani.BEArtGallery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private AuthService authService;


    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "uuid") String orderBy) {
        return userService.getUsers(page, size, orderBy);
    }

    // /me endpoints
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }


    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentUser, @RequestBody UserDTO body) {
        return authService.findByIdAndUpdate(currentUser.getUuid(), body);
    }

    @DeleteMapping("/me")
    public void getMeAnDelete(@AuthenticationPrincipal User currentUser) {
        userService.deleteById(currentUser.getUuid());
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return userService.findById(userId);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody UserDTO modifiedUserPayload) {
        return authService.findByIdAndUpdate(userId, modifiedUserPayload);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getUserByIdAndDelete(@PathVariable UUID userId) {
        userService.deleteById(userId);
    }

    @PostMapping("/{uuid}/avatar")
    public String uploadExample(@PathVariable UUID uuid, @RequestParam("avatar") MultipartFile body) throws IOException {
        return userService.uploadPicture(uuid, body);
    }
}

