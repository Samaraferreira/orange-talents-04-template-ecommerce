package br.com.zup.edu.ecommerce.category;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @PostMapping
    private ResponseEntity<?> create(@Valid @RequestBody CategoryRequest request) {
        Category category = request.toModel(repository);

        repository.save(category);

        return ResponseEntity.ok().build();
    }

}
