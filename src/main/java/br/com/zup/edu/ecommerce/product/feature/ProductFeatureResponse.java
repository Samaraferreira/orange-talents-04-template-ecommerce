package br.com.zup.edu.ecommerce.product.feature;

public class ProductFeatureResponse {

    private String description;
    private String name;

    public ProductFeatureResponse(ProductFeature model) {
        this.name = model.getName();
        this.description = model.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
