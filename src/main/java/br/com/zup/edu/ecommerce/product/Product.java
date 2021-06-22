package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.category.Category;
import br.com.zup.edu.ecommerce.product.feature.ProductFeature;
import br.com.zup.edu.ecommerce.product.feature.ProductFeatureRequest;
import br.com.zup.edu.ecommerce.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private User user;

    @Deprecated
    public Product() {
    }

    public Product(String name,
                   BigDecimal price,
                   Integer quantity,
                   String description,
                   List<ProductFeatureRequest> features,
                   Category category,
                   User user) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.features.addAll(features.stream().map(f -> f.toModel(this)).collect(Collectors.toSet()));
        this.category = category;
        this.user = user;
    }
}
