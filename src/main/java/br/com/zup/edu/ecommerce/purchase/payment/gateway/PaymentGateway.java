package br.com.zup.edu.ecommerce.purchase.payment.gateway;

import java.net.URI;
import java.util.UUID;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface PaymentGateway {
    URI getRedirectionURI(UUID paymentId);

    default String getReturnLink() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .replacePath("/payments")
                .toUriString();
    }
}
