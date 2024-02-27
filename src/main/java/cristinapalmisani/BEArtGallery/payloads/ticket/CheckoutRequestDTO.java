package cristinapalmisani.BEArtGallery.payloads.ticket;

import java.util.List;



public record CheckoutRequestDTO (String title,
                                  String image,
                                  String date,
                                  double amount,
                                  long maxNum,
                                  String hour,
                                   List<TypeTicketDTO> typeTicket) {



}
