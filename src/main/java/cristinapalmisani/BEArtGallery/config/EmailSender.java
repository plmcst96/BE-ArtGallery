package cristinapalmisani.BEArtGallery.config;

import cristinapalmisani.BEArtGallery.entities.User;
import cristinapalmisani.BEArtGallery.payloads.email.EmailDTO;
import cristinapalmisani.BEArtGallery.payloads.formCurator.FormDataCuratorDTO;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void sendRegistrationEmailCurator(User recipient) {

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", this.mailFrom)
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Request accepted")
                .queryString("text", "http://localhost:3001/auth/registration-curator")
                .asJson();
        System.out.println(response);
    }

    public void  sendEmailToAdmin(FormDataCuratorDTO formDataCuratorDTO, boolean accepted){
        if (accepted){
            String acceptLink = "http://localhost:3001/users/" + formDataCuratorDTO.email() + "/setAccepted";

            String emailBody = "<p>Your request has been accepted. Please click the button below to proceed:</p>" +
                    "<a href=\"" + acceptLink + "\"><button style=\"background-color: #4CAF50; /* Green */\n" +
                    "  border: none;\n" +
                    "  color: white;\n" +
                    "  padding: 15px 32px;\n" +
                    "  text-align: center;\n" +
                    "  text-decoration: none;\n" +
                    "  display: inline-block;\n" +
                    "  font-size: 16px;\n" +
                    "  margin: 4px 2px;\n" +
                    "  cursor: pointer;\">Accept Request</button></a>";


        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomainname + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", formDataCuratorDTO.email())
                .queryString("to", this.adminEmail)
                .queryString("subject", "New request to work with us")
                .queryString("html", formDataCuratorDTO.name() + " " + formDataCuratorDTO.surname() + " " + formDataCuratorDTO.descriptionRole() + " " + emailBody)
                .asJson();
        System.out.println(response);
        }
    }
}

