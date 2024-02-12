package cristinapalmisani.BEArtGallery.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundException extends RuntimeException{
    public NotFoundException(UUID uuid) {
        super("id " + uuid + " not found!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
