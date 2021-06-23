package br.com.zup.edu.ecommerce.purchase;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.purchase.payment.PaymentType;
import br.com.zup.edu.ecommerce.user.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseRequest {

    @NotNull @Positive
    private Integer quantity;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private Long productId;

    public PurchaseRequest(Integer quantity, PaymentType paymentType, Long productId) {
        this.quantity = quantity;
        this.paymentType = paymentType;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public Purchase toModel(User purchaser, Product product) {
        return new Purchase(this.quantity,  this.paymentType, purchaser, product);
    }
}
