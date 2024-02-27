package cristinapalmisani.BEArtGallery.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import cristinapalmisani.BEArtGallery.entities.TypeTicket;
import cristinapalmisani.BEArtGallery.payloads.ticket.CheckoutRequestDTO;
import cristinapalmisani.BEArtGallery.payloads.ticket.TypeTicketDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.checkout.Session;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class CheckoutController {
    @Value("${stripe.apikey}")
    private String stripeApiKey;
    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody CheckoutRequestDTO checkoutRequest) throws StripeException {
        // Imposta la chiave API di Stripe
        Stripe.apiKey = stripeApiKey;
        String title = checkoutRequest.title();
        String date = checkoutRequest.date();
        String hour = checkoutRequest.hour();
        double amount = checkoutRequest.amount();
        long maxNum = checkoutRequest.maxNum();
        List<TypeTicketDTO> typeTicket = checkoutRequest.typeTicket();

        // Calcola gli elementi di linea per il checkout
        List<SessionCreateParams.LineItem> lineItems = buildLineItems(title, amount, maxNum, typeTicket);

        // Crea la sessione di checkout con gli elementi di linea
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/success")
                .setCancelUrl("http://localhost:5173/cancel")
                .addAllLineItem(lineItems)
                .build();


        com.stripe.model.checkout.Session session = com.stripe.model.checkout.Session.create(params);
        // Restituisci il clientSecret e l'URL di checkout
        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", session.getClientSecret());
        response.put("checkoutUrl", session.getUrl());
        System.out.println(response);

        return response;
    }

    private SessionCreateParams.LineItem buildLineItem(String title, double amount, String type, long quantity) {
        // Verifica se il tipo è null o vuoto, in tal caso imposta un valore predefinito
        if (type == null || type.isEmpty()) {
            type = "STANDARD"; // Imposta il tipo predefinito
        }

        try {
            // Converti la stringa type in un valore dell'enum TypeTicket
            TypeTicket typeTicket = TypeTicket.valueOf(type.toUpperCase());

            // Calcola il prezzo del biglietto in base al tipo
            double ticketPrice = calculateTicketPrice(amount, typeTicket);

            // Creazione dell'elemento di linea per il biglietto
            return SessionCreateParams.LineItem.builder()
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("eur")
                            .setUnitAmount((long) (ticketPrice * 100))
                            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(title)
                                    .build())
                            .build())
                    .setQuantity(quantity)
                    .build();
        } catch (IllegalArgumentException e) {
            // Se il tipo non corrisponde a nessun valore dell'enum, gestisci l'errore
            System.err.println("Tipo di biglietto non valido: " + type);
            // Puoi gestire l'errore restituendo null o lanciando un'eccezione
            return null;
        }
    }

    private List<SessionCreateParams.LineItem> buildLineItems(String title, double amount, long maxNum, List<TypeTicketDTO> typeTicket) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        // Calcola gli elementi di linea per ciascun tipo di biglietto
        List<SessionCreateParams.LineItem> ticketLineItems = typeTicket.stream()
                .map(ticket -> buildLineItem(title, amount, ticket.type(), ticket.quantity()))
                .collect(Collectors.toList());

        lineItems.addAll(ticketLineItems);

        return lineItems;
    }

    private double calculateTicketPrice(double eventAmount, TypeTicket typeTicket) {
        double basePrice = eventAmount;
        double ticketPrice = basePrice;

        switch (typeTicket) {
            case STANDARD:
                ticketPrice = basePrice;
                break;
            case UNDER_7:
                ticketPrice *= 0.5;
                break;
            case OVER_60:
                ticketPrice *= 0.8;
                break;
            case STUDENTS:
                ticketPrice *= 0.75;
                break;
            default:
                break;
        }

        return ticketPrice;
    }
}