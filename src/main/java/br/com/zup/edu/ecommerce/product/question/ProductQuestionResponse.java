package br.com.zup.edu.ecommerce.product.question;

import java.time.LocalDateTime;

public class ProductQuestionResponse {

    private String title;
    private LocalDateTime createdAt;

    public ProductQuestionResponse(ProductQuestion model) {
        this.title = model.getTitle();
        this.createdAt = model.getCreatedAt();
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
