package br.com.zup.edu.ecommerce.product.opinion;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text", length = 500)
    private String description;

    @ManyToOne(optional = false)
    private User author;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Deprecated
    public ProductOpinion() {
    }

    public ProductOpinion(Integer rating, String title, String description, User user, Product product) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.author = user;
        this.product = product;
    }

    public Integer getRating() {
        return rating;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }

    public Product getProduct() {
        return product;
    }
}
