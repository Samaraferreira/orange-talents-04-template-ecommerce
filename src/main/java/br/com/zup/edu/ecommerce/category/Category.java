package br.com.zup.edu.ecommerce.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private @NotBlank String name;

    @ManyToOne
    private Category parent;

    /**
     * @deprecated for framework use only
     * */
    @Deprecated
    public Category() {
    }

    public Category(@NotBlank String name) {
        this.name = name;
    }

    public void setParent(Category category) {
        this.parent = category;
    }
}
