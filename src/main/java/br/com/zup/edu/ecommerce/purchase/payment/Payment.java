package br.com.zup.edu.ecommerce.purchase.payment;

import br.com.zup.edu.ecommerce.purchase.Purchase;
import java.net.URI;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Purchase purchase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Deprecated
    public Payment() {
    }

    public Payment(Purchase purchase, PaymentType type) {
        this.purchase = purchase;
        this.paymentType = type;
    }

    public URI getPaymentLink() {
        return paymentType.getPaymentGateway().getRedirectionURI(id);
    }
}
