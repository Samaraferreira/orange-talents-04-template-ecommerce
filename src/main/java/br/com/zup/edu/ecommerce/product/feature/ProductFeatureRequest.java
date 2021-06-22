package br.com.zup.edu.ecommerce.product.feature;

import br.com.zup.edu.ecommerce.product.Product;
import javax.validation.constraints.NotBlank;

public class ProductFeatureRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public ProductFeatureRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProductFeature toModel(Product product) {
        return new ProductFeature(name, description, product);
    }

    public String getName() {
        return name;
    }

}
