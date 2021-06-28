package br.com.zup.edu.ecommerce.category;

import br.com.zup.edu.ecommerce.shared.validation.UniqueValue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CategoryRequest {

    private @NotBlank @UniqueValue(domainClass = Category.class, fieldName = "name") String name;
    private @Positive Long parentId;

    @Deprecated
    public CategoryRequest() {
    }

    public CategoryRequest(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public Category toModel(Category parent) {
        var category = new Category(this.name);

        if (parent != null) {
            category.setParent(parent);
        }

        return category;
    }
}
