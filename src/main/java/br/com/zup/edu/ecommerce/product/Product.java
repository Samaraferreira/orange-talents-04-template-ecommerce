package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.category.Category;
import br.com.zup.edu.ecommerce.product.feature.ProductFeature;
import br.com.zup.edu.ecommerce.product.feature.ProductFeatureRequest;
import br.com.zup.edu.ecommerce.product.images.Image;
import br.com.zup.edu.ecommerce.product.opinion.ProductOpinion;
import br.com.zup.edu.ecommerce.product.question.ProductQuestion;
import br.com.zup.edu.ecommerce.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "text", length = 1000)
    private String description;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ProductFeature> features = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductOpinion> opinions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("createdAt desc")
    private List<ProductQuestion> questions = new ArrayList<>();

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private User owner;

    @Deprecated
    public Product() {
    }

    public Product(String name,
                   BigDecimal price,
                   Integer quantity,
                   String description,
                   List<ProductFeatureRequest> features,
                   Category category,
                   User owner) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.features.addAll(features.stream().map(f -> f.toModel(this)).collect(Collectors.toSet()));
        this.category = category;
        this.owner = owner;
    }

    public void addImages(Set<String> links) {
        this.images.addAll(links.stream().map(link -> new Image(link, this)).collect(Collectors.toList()));
    }

    public void addOpinion(ProductOpinion opinion) {
        this.opinions.add(opinion);
    }

    public void addQuestion(ProductQuestion question) {
        this.questions.add(question);
    }

    public Double getAverageRating() {
        OptionalDouble average = this.opinions.stream()
                                                .mapToInt(ProductOpinion::getRating)
                                                .asDoubleStream()
                                                .average();
        if (average.isEmpty()) return null;
        return average.getAsDouble();
    }

    public boolean decrementQuantity(@Positive Integer quantity) {
        if (this.quantity < quantity) return false;
        this.quantity -= quantity;
        return true;
    }

    public int getTotalRatings() {
        return this.opinions.size();
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<ProductFeature> getFeatures() {
        return features;
    }

    public List<Image> getImages() {
        return images;
    }

    public Category getCategory() {
        return category;
    }

    public List<ProductOpinion> getOpinions() {
        return opinions;
    }

    public List<ProductQuestion> getQuestions() {
        return questions;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
