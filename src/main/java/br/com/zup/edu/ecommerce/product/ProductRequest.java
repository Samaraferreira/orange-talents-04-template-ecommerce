package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.category.Category;
import br.com.zup.edu.ecommerce.product.feature.ProductFeatureRequest;
import br.com.zup.edu.ecommerce.user.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProductRequest {

    @NotBlank
    private String name;

    @NotNull @Positive
    private BigDecimal price;

    @NotNull @Positive
    private Integer quantity;

    @NotBlank @Size(max = 1000)
    private String description;

    @NotNull @Size(min = 3, max = 200)
    private List<@Valid ProductFeatureRequest> features;

    @NotNull
    private Long categoryId;

    public ProductRequest(String name, BigDecimal price, Integer quantity, String description, List<@Valid ProductFeatureRequest> features, Long categoryId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.features = features;
        this.categoryId = categoryId;
    }

    public Product toModel(Category category, User user) {
        return new Product(name, price, quantity, description, features, category, user);
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<ProductFeatureRequest> getFeatures() {
        return features;
    }

    public Set<String> findDuplicatedFeatures() {
        return features.stream()
                .map(ProductFeatureRequest::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
