package br.com.zup.edu.ecommerce.category;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @PostMapping
    private ResponseEntity<?> create(@Valid @RequestBody CategoryRequest request) {

        if (request.getParentId() != null) {
            Optional<Category> parent = repository.findById(request.getParentId());

            if (parent.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
            }

            Category category = request.toModel(parent.get());
            repository.save(category);
        } else {
            Category category = request.toModel(null);
            repository.save(category);
        }

        return ResponseEntity.ok().build();
    }

}
