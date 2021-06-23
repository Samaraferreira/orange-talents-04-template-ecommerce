package br.com.zup.edu.ecommerce.purchase;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.purchase.payment.Payment;
import br.com.zup.edu.ecommerce.purchase.payment.PaymentType;
import br.com.zup.edu.ecommerce.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Purchase {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal productValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus status = PurchaseStatus.STARTED;

    @OneToOne(optional = false, mappedBy = "purchase", cascade = CascadeType.ALL)
    private Payment payment;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private User purchaser;

    @Deprecated
    public Purchase() {
    }

    public Purchase(Integer quantity, PaymentType paymentType, User purchaser, Product product) {
        this.quantity = quantity;
        this.product = product;
        this.purchaser = purchaser;
        this.productValue = product.getPrice();
        this.payment = new Payment(this, paymentType);
    }

    public User getProductOwner() {
        return this.product.getOwner();
    }

    public User getPurchaser() {
        return purchaser;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Payment getPayment() {
        return payment;
    }
}
