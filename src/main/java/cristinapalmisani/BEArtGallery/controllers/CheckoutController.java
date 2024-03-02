package cristinapalmisani.BEArtGallery.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountSession;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSession;
import com.stripe.model.checkout.Session;
import com.stripe.param.*;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import cristinapalmisani.BEArtGallery.entities.TypeTicket;
import cristinapalmisani.BEArtGallery.payloads.ticket.CheckoutRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/v1")
public class CheckoutController {
    @Value("${stripe.apikey}")
    private String stripeApiKey;


    @PostMapping("/accounts")
    public Account createAccount (@RequestBody String email) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        AccountCreateParams params =
                AccountCreateParams.builder()
                        .setType(AccountCreateParams.Type.CUSTOM)
                        .setCountry("IT")
                        .setEmail(email)
                        .setCapabilities(
                                AccountCreateParams.Capabilities.builder()
                                        .setCardPayments(
                                                AccountCreateParams.Capabilities.CardPayments.builder()
                                                        .setRequested(true)
                                                        .build()
                                        )
                                        .setTransfers(
                                                AccountCreateParams.Capabilities.Transfers.builder()
                                                        .setRequested(true)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();




        return Account.create(params);
    }

    @PostMapping("/account_sessions")
    public Map<String, String> createAccountSession() throws StripeException {
        Stripe.apiKey = stripeApiKey;
        AccountSessionCreateParams params =
                AccountSessionCreateParams.builder()
                        .setAccount("acct_1OpGUQGg7v071X5F")
                        .setComponents(
                                AccountSessionCreateParams.Components.builder()
                                        .setAccountOnboarding(
                                                AccountSessionCreateParams.Components.AccountOnboarding.builder()
                                                        .setEnabled(true)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        AccountSession accountSession = AccountSession.create(params);


        // Accetta i termini di Stripe per l'account
        Account resource = Account.retrieve("acct_1OpGUQGg7v071X5F");
        AccountUpdateParams updateParams = AccountUpdateParams.builder()
                .setTosAcceptance(
                        AccountUpdateParams.TosAcceptance.builder()
                                .setDate(Instant.now().getEpochSecond())
                                .setIp("79.9.7.70")  // L'IP può essere ottenuto dal client se necessario
                                .build()
                )
                .build();
        resource.update(updateParams);

        // Ottieni il client secret dalla sessione di account


        // Restituisci i dettagli della sessione di account insieme al client secret
        Map<String, String> response = new HashMap<>();
        response.put("accountSessionId", accountSession.getAccount());
        response.put("clientSecret", accountSession.getClientSecret());
        return response;
    }

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody CheckoutRequestDTO checkoutRequest) throws StripeException {

        // Effettua la richiesta per ottenere il clientSecret
        Map<String, String> accountSessionResponse = createAccountSession();
        String clientSecret = accountSessionResponse.get("clientSecret");
        // Imposta la chiave API di Stripe
        Stripe.apiKey = stripeApiKey;

        String title = checkoutRequest.title();
        double amount = checkoutRequest.amount();
        long maxNum = checkoutRequest.maxNum();
        String date = checkoutRequest.date();
        String hour = checkoutRequest.hour();
        String image = checkoutRequest.image();
        Map<String, Long> typeTicket = checkoutRequest.typeTicket();


        // Calcola gli elementi di linea per il checkout
        List<SessionCreateParams.LineItem> lineItems = buildLineItems(title, image ,date, amount,maxNum, hour,typeTicket);

        // Crea la sessione di checkout con gli elementi di linea
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/success")
                .setCancelUrl("http://localhost:5173/cancel")
                .addAllLineItem(lineItems)
                .build();
        try {
            Session session = Session.create(params);
            // Restituisci il clientSecret e l'URL di checkout
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", clientSecret);
            System.out.println("clientSecret " + session.getClientSecret());
            response.put("checkoutUrl", session.getUrl());
            response.put("sessionId", session.getId());

            return response;
        } catch (StripeException e) {
            log.error("Errore durante la creazione della sessione di checkout: {}", e.getMessage());
            throw new RuntimeException("Errore durante la creazione della sessione di checkout", e);
        }
    }

    private List<SessionCreateParams.LineItem> buildLineItems(String title, String image, String date, double amount, long maxNum, String hour, Map<String, Long> typeTickets) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        // Verifica se typeTickets è null prima di iterare su di esso
        if (typeTickets != null) {
            // Calcola gli elementi di linea per ciascun tipo di biglietto
            for (Map.Entry<String, Long> entry : typeTickets.entrySet()) {
                String type = entry.getKey();
                long quantity = entry.getValue();
                SessionCreateParams.LineItem lineItem = buildLineItem(title,image,date, amount, maxNum, hour, type, quantity );
                if (lineItem != null) {
                    lineItems.add(lineItem);
                }
            }
        }

        // Verifica se la lista degli elementi di linea è vuota prima di passarla alla creazione della sessione di checkout
        if (lineItems.isEmpty()) {
            log.error("La lista degli elementi di linea è vuota. Assicurati che i dati siano corretti.");
            throw new IllegalArgumentException("La lista degli elementi di linea non può essere vuota");
        }

        return lineItems;
    }

    private SessionCreateParams.LineItem buildLineItem(String title, String image, String date, double amount, long maxNum, String hour, String type, long quantity) {
        try {
            // Converti la stringa type in un valore dell'enum TypeTicket
            TypeTicket typeTicket = TypeTicket.valueOf(type.toUpperCase());

            // Calcola il prezzo del biglietto in base al tipo
            double ticketPrice = calculateTicketPrice(amount, typeTicket);

            // Costruisci la descrizione dell'elemento di linea
            String description = String.format("%s - Date: %s, Hour: %s - %s - Image: %s", title, date, hour, type, image);

            // Creazione dell'elemento di linea per il biglietto
            return SessionCreateParams.LineItem.builder()
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("eur")
                            .setUnitAmount((long) (ticketPrice * 100))
                            .setProductData(LineItem.PriceData.ProductData.builder()
                                    .setName(description)
                                    .build())
                            .build())
                    .setQuantity(quantity)
                    .build();
        } catch (IllegalArgumentException e) {
            log.error("Tipo di biglietto non valido: {}", type);
            return null;
        }
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