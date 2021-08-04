package br.com.zup.edu.ecommerce.product;

import br.com.zup.edu.ecommerce.category.Category;
import br.com.zup.edu.ecommerce.category.CategoryRepository;
import br.com.zup.edu.ecommerce.product.images.ImageUpload;
import br.com.zup.edu.ecommerce.product.images.ImagesRequest;
import br.com.zup.edu.ecommerce.product.opinion.ProductOpinion;
import br.com.zup.edu.ecommerce.product.opinion.ProductOpinionRequest;
import br.com.zup.edu.ecommerce.product.question.ProductQuestion;
import br.com.zup.edu.ecommerce.product.question.ProductQuestionRequest;
import br.com.zup.edu.ecommerce.shared.security.LoggedUser;
import br.com.zup.edu.ecommerce.utils.email.Emails;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(tags = "Products")
@RestController
@RequestMapping(value = "/api/v1/products", produces="application/json")
@Validated
public class ProductController {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ImageUpload imageUpload;
    private Emails emails;

    @InitBinder(value = "productRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new DistinctFeatureNameValidator());
    }

    @Autowired
    public ProductController(CategoryRepository categoryRepository,
                             ProductRepository productRepository,
                             ImageUpload imageUpload,
                             Emails emails) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.imageUpload = imageUpload;
        this.emails = emails;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProductRequest request, @AuthenticationPrincipal LoggedUser loggedUser) {
        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategoryId());

        if (categoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        Product product = request.toModel(categoryOptional.get(), loggedUser.getUser());
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Retorna um produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto solicitado"),
            @ApiResponse(code = 401, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Produto não encontrado")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> details(@PathVariable Long productId) {
        Product product = findProduct(productId);

        return ResponseEntity.ok().body(new ProductResponse(product));
    }


    @PostMapping(value = "/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImages(@PathVariable Long productId,
                                       @ModelAttribute @Valid ImagesRequest request,
                                       @AuthenticationPrincipal LoggedUser loggedUser) {

        Product product = findProduct(productId);

        if (!product.getOwner().equals(loggedUser.getUser())) {
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

    @PostMapping("/{productId}/opinions")
    public ResponseEntity<?> addOpinion(@PathVariable Long productId,
                                             @RequestBody @Valid ProductOpinionRequest request,
                                             @AuthenticationPrincipal LoggedUser loggedUser) {

        Product product = findProduct(productId);
        ProductOpinion productOpinion = request.toModel(loggedUser.getUser(), product);
        product.addOpinion(productOpinion);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/questions")
    public ResponseEntity<?> addQuestion(@PathVariable Long productId,
                                         @RequestBody @Valid ProductQuestionRequest request,
                                         @AuthenticationPrincipal LoggedUser loggedUser) {

        Product product = findProduct(productId);
        ProductQuestion question = request.toModel(loggedUser.getUser(), product);
        product.addQuestion(question);
        productRepository.save(product);
        emails.newQuestion(question);

        return ResponseEntity.ok().build();
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}
