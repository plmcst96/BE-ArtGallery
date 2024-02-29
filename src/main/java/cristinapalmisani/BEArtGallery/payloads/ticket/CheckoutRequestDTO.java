package cristinapalmisani.BEArtGallery.payloads.ticket;
import java.util.Map;


public record CheckoutRequestDTO (String title,
                                  String image,
                                  String date,
                                  double amount,
                                  long maxNum,
                                  String hour,
                                  Map<String, Long> typeTicket
                                ) {



}
