package br.com.zup.edu.ecommerce.purchase.payment;

import br.com.zup.edu.ecommerce.purchase.payment.gateway.Pagseguro;
import br.com.zup.edu.ecommerce.purchase.payment.gateway.PaymentGateway;
import br.com.zup.edu.ecommerce.purchase.payment.gateway.Paypal;

public enum PaymentType {
    PAYPAL(new Paypal()),
    PAGSEGURO(new Pagseguro());

    private PaymentGateway paymentGateway;

    PaymentType(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }
}
