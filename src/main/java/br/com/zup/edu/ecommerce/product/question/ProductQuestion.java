package br.com.zup.edu.ecommerce.product.question;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class ProductQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    private User author;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Deprecated
    public ProductQuestion() {
    }

    public ProductQuestion(String title, User author, Product product) {
        this.title = title;
        this.author = author;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public User getAuthor() {
        return author;
    }

    public User getProductOwner() {
        return product.getOwner();
    }
}
