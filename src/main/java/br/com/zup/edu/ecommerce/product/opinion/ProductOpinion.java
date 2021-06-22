package br.com.zup.edu.ecommerce.product.opinion;

import br.com.zup.edu.ecommerce.product.Product;
import br.com.zup.edu.ecommerce.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private User user;

    @ManyToOne(optional = false)
    private Product product;

    @Deprecated
    public ProductOpinion() {
    }

    public ProductOpinion(Integer rating, String title, String description, User user, Product product) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.user = user;
        this.product = product;
    }
}
