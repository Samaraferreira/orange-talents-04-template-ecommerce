package br.com.zup.edu.ecommerce.category;

import br.com.zup.edu.ecommerce.shared.validation.UniqueValue;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryRequest {

    private @NotBlank @UniqueValue(domainClass = Category.class, fieldName = "name") String name;
    private @Positive Long parentId;

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public Category toModel(CategoryRepository repository) {
        var category = new Category(name);

        if (parentId != null) {
            Optional<Category> parent = repository.findById(parentId);

            if (parent.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
            }
            category.setParent(parent.get());
//            parent.ifPresent(category::setMotherCategory);
        }

        return category;
    }
}
