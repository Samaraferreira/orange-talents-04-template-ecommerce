package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.product.feature.ProductFeatureResponse;
import br.com.zup.edu.ecommerce.product.images.Image;
import br.com.zup.edu.ecommerce.product.opinion.ProductOpinionResponse;
import br.com.zup.edu.ecommerce.product.question.ProductQuestionResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductResponse {

    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Set<ProductFeatureResponse> features;
    private List<ProductOpinionResponse> opinions;
    private List<ProductQuestionResponse> questions;
    private List<String> images = new ArrayList<>();
    private Double averageRating;
    private Integer totalRatings;

    public ProductResponse(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.images = product.getImages().stream().map(Image::getLink).collect(Collectors.toList());
        this.features = product.getFeatures().stream().map(ProductFeatureResponse::new).collect(Collectors.toSet());
        this.opinions = product.getOpinions().stream().map(ProductOpinionResponse::new).collect(Collectors.toList());
        this.questions = product.getQuestions().stream().map(ProductQuestionResponse::new).collect(Collectors.toList());
        this.averageRating = product.getAverageRating();
        this.totalRatings = product.getTotalRatings();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Set<ProductFeatureResponse> getFeatures() {
        return features;
    }

    public List<ProductOpinionResponse> getOpinions() {
        return opinions;
    }

    public List<ProductQuestionResponse> getQuestions() {
        return questions;
    }

    public List<String> getImages() {
        return images;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }
}
