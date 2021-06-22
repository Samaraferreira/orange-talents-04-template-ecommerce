package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.category.Category;
import br.com.zup.edu.ecommerce.category.CategoryRepository;
import br.com.zup.edu.ecommerce.shared.security.LoggedUser;
import br.com.zup.edu.ecommerce.user.User;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @InitBinder(value = "productRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new DistinctFeatureNameValidator());
    }

    @Autowired
    public ProductController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ProductRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategoryId());

        if (categoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        Product product = request.toModel(categoryOptional.get(), loggedUser.getUser());

        productRepository.save(product);

        return ResponseEntity.ok().build();
    }
}
