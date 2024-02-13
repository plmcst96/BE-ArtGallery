package cristinapalmisani.BEArtGallery.controllers;

import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.exception.BadRequestException;
import cristinapalmisani.BEArtGallery.payloads.user.UserDTO;
import cristinapalmisani.BEArtGallery.payloads.user.UserLoginDTO;
import cristinapalmisani.BEArtGallery.payloads.user.UserLoginResponseDTO;
import cristinapalmisani.BEArtGallery.payloads.user.UserResponseDTO;
import cristinapalmisani.BEArtGallery.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        Map<String, String> authResponse = authService.authenticateUser(body);
        return new UserLoginResponseDTO(authResponse.get("token"), authResponse.get("role"));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException("Ci sono errori nel payload!");
        }else {
            User newUser = authService.registerUser(newUserPayload);

            return new UserResponseDTO(newUser.getUuid());
        }
    }

    @PostMapping("/registerCurator")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUserCurator(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException("Ci sono errori nel payload!");
        }else {
            User newUser = authService.registerCurator(newUserPayload);

            return new UserResponseDTO(newUser.getUuid());
        }
    }

    }
