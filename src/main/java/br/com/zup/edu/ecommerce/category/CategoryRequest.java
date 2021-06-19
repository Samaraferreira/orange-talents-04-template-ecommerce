package br.com.zup.edu.ecommerce.category;

import br.com.zup.edu.ecommerce.shared.validation.UniqueValue;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import org.springframework.util.Assert;

public class CategoryRequest {

    private @NotBlank @UniqueValue(domainClass = Category.class, fieldName = "name") String name;
    private @Positive Long motherCategoryId;

    public void setName(String name) {
        this.name = name;
    }

    public void setMotherCategoryId(Long motherCategoryId) {
        this.motherCategoryId = motherCategoryId;
    }

    public Long getMotherCategoryId() {
        return motherCategoryId;
    }

    public Category toModel(CategoryRepository repository) {
        var category = new Category(name);

        if (motherCategoryId != null) {
            Optional<Category> motherCategory = repository.findById(motherCategoryId);
            motherCategory.ifPresent(category::setMotherCategory);
        }

        return category;
    }
}
