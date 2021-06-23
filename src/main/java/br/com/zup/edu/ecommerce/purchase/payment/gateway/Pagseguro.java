package br.com.zup.edu.ecommerce.purchase.payment.gateway;

import java.net.URI;
import java.util.UUID;
import org.springframework.web.util.UriComponentsBuilder;

public class Pagseguro implements PaymentGateway {

    private static final String GATEWAY_URL = "https://pagseguro.com";
    private static final String PURCHASE_ID_PARAM = "buyerId";
    private static final String REDIRECT_PARAM = "redirectUrl";

    @Override
    public URI getRedirectionURI(UUID paymentId) {
        return UriComponentsBuilder.fromHttpUrl(GATEWAY_URL)
                .queryParam(PURCHASE_ID_PARAM, paymentId)
                .queryParam(REDIRECT_PARAM, getReturnLink())
                .build()
                .toUri();
    }
}
