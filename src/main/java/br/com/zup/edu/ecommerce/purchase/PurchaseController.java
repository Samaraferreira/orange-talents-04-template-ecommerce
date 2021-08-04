package br.com.zup.edu.ecommerce.purchase;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.product.ProductRepository;
import br.com.zup.edu.ecommerce.purchase.payment.Payment;
import br.com.zup.edu.ecommerce.shared.security.LoggedUser;
import br.com.zup.edu.ecommerce.utils.email.Emails;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Api(tags = "Purchases")
@RestController
@RequestMapping(value = "/api/v1/purchase", produces="application/json", consumes="application/json")
public class PurchaseController {

    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private Emails emails;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository, ProductRepository productRepository, Emails emails) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.emails = emails;
    }

    @PostMapping
    public ResponseEntity<?> newPurchase(@RequestBody @Valid PurchaseRequest request,
                                              @AuthenticationPrincipal LoggedUser loggedUser) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Purchase purchase = request.toModel(loggedUser.getUser(), product);

        if(!product.decrementQuantity(purchase.getQuantity())) {
            return ResponseEntity.badRequest().body("Selected product is unavailable");
        }

        Payment payment = purchase.getPayment();

        purchaseRepository.save(purchase);
        emails.newPurchase(purchase);

        return ResponseEntity.status(HttpStatus.FOUND).location(payment.getPaymentLink()).build();
    }

}
