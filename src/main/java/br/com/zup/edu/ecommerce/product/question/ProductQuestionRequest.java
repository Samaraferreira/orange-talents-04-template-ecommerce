package br.com.zup.edu.ecommerce.product.question;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.user.User;
import javax.validation.constraints.NotBlank;

public class ProductQuestionRequest {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductQuestion toModel(User user, Product product) {
        return new ProductQuestion(title, user, product);
    }
}
