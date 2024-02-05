package cristinapalmisani.BEArtGallery.payloads.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorPayloadWithListDTO extends ErrorPayload{
     List<String> errorsList;

    public ErrorPayloadWithListDTO(String message, Date timestamp, List<String> errorsList) {
        super(message, timestamp);
        this.errorsList = errorsList;
    }
}
