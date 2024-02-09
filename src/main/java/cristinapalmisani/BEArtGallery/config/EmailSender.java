package cristinapalmisani.BEArtGallery.config;

import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.payloads.email.EmailDTO;
import cristinapalmisani.BEArtGallery.payloads.formCurator.FormDataCuratorDTO;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private String mailgunApiKey;
    private String mailgunDomainname;
    private String adminEmail;

    @Value("${mailgun.maildefault}")
    private String mailFrom;

    public EmailSender(@Value("${mailgun.apikey}") String mailgunApiKey,
                       @Value("${mailgun.domainname}") String mailgunDomainName,
                       @Value("${mailgun.maildefault}") String adminEmail) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomainname = mailgunDomainName;
        this.adminEmail = adminEmail;
    }


    public void sendRegistrationEmail(User recipient, EmailDTO emailDTO) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailFrom)
                .queryString("to", recipient.getEmail())
                .queryString("subject", emailDTO.subject())
                .queryString("text", emailDTO.content())
                .asJson();
        System.out.println(response);
    }

    public void  sendEmailToAdmin(FormDataCuratorDTO formDataCuratorDTO){
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", formDataCuratorDTO.email())
                .queryString("to", this.adminEmail)
                .queryString("subject", "New request to work with us")
                .queryString("text", "Nome: " + formDataCuratorDTO.name() + "Surname: " + formDataCuratorDTO.surname() + "Description role: " + formDataCuratorDTO.descriptionRole())
                .asJson();
        System.out.println(response);
    }
}

