package br.com.zup.edu.ecommerce.product.opinion;

import br.com.zup.edu.ecommerce.user.User;

public class ProductOpinionResponse {

    private Integer rating;
    private String title;
    private String description;

    public ProductOpinionResponse(ProductOpinion model) {
        this.rating = model.getRating();
        this.title = model.getTitle();
        this.description = model.getDescription();
    }

    public Integer getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
