package br.com.zup.edu.ecommerce.product.images;

import br.com.zup.edu.ecommerce.product.Product;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) @NotNull @URL
    private String link;

    @ManyToOne(optional = false)
    private Product product;

    @Deprecated
    public Image() {
    }

    public Image(String link, Product product) {
        this.link = link;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
