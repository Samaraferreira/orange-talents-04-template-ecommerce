package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.category.Category;
import br.com.zup.edu.ecommerce.category.CategoryRepository;
import br.com.zup.edu.ecommerce.product.images.Image;
import br.com.zup.edu.ecommerce.product.images.ImageUpload;
import br.com.zup.edu.ecommerce.product.images.ImagesRequest;
import br.com.zup.edu.ecommerce.shared.security.LoggedUser;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ImageUpload imageUpload;

    @InitBinder(value = "productRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new DistinctFeatureNameValidator());
    }

    @Autowired
    public ProductController(CategoryRepository categoryRepository, ProductRepository productRepository, ImageUpload imageUpload) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.imageUpload = imageUpload;
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

    @PostMapping(value = "{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImages(@PathVariable Long productId,
                                       @ModelAttribute @Valid ImagesRequest request,
                                       @AuthenticationPrincipal LoggedUser loggedUser) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!product.getUser().equals(loggedUser.getUser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have permission");
        }

        try {
            final Set<String> links = imageUpload.upload(request.getImages());

            product.addImages(links);

            productRepository.save(product);

            return ResponseEntity.ok().build();
        } catch (IOException exception) {
            return ResponseEntity.internalServerError().body("Unable to save images");
        }
    }
}
