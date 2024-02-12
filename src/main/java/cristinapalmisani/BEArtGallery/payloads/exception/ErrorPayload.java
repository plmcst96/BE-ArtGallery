package cristinapalmisani.BEArtGallery.payloads.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ErrorPayload {
    private String message;
    private Date timestamp;
}
