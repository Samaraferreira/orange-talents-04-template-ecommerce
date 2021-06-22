package br.com.zup.edu.ecommerce.product.opinion;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.user.User;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductOpinionRequest {

    @NotNull @Min(1) @Max(5)
    private Integer rating;

    @NotBlank
    private String title;

    @NotBlank @Size(max = 500)
    private String description;

    public ProductOpinionRequest(Integer rating, String title, String description) {
        this.rating = rating;
        this.title = title;
        this.description = description;
    }

    public ProductOpinion toModel(User user, Product product) {
        return new ProductOpinion(rating, title, description, user, product);
    }

}
