package br.com.zup.edu.ecommerce.category;

import br.com.zup.edu.ecommerce.shared.security.LoginRequest;
import br.com.zup.edu.ecommerce.shared.security.LoginResponse;
import br.com.zup.edu.ecommerce.user.User;
import br.com.zup.edu.ecommerce.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String CATEGORY_NAME = "Cozinha";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void deveCadastrarUmaCategoria() throws Exception {
        var ACCESS_TOKEN = obtainAccessToken();
        CategoryRequest newCategoryRequest = new CategoryRequest(CATEGORY_NAME);

        mockMvc
            .perform(post("/api/v1/categories")
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .contentType("application/json;charset=UTF-8")
                    .content(objectMapper.writeValueAsString(newCategoryRequest)))
            .andExpect(status().isOk());
    }

    @Test
    void deveCadastrarUmaCategoriaComCategoriaPai() throws Exception {
        var ACCESS_TOKEN = obtainAccessToken();
        Category category = new Category("Casa, Móveis e Decoração");
        categoryRepository.save(category);

        CategoryRequest newCategoryRequest = new CategoryRequest(CATEGORY_NAME);
        newCategoryRequest.setParentId(category.getId());

        mockMvc
            .perform(post("/api/v1/categories")
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .contentType("application/json;charset=UTF-8")
                    .content(objectMapper.writeValueAsString(newCategoryRequest)))
            .andExpect(status().isOk());
    }

    @Test
    void naoDeveCadastrarUmaCategoriaQuandoNomeJaExistir() throws Exception {
        var ACCESS_TOKEN = obtainAccessToken();

        Category category = new Category(CATEGORY_NAME);
        categoryRepository.save(category);

        CategoryRequest newCategoryRequest = new CategoryRequest(category.getName());

        mockMvc
                .perform(post("/api/v1/categories")
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .contentType("application/json;charset=UTF-8")
                        .content(objectMapper.writeValueAsString(newCategoryRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmaCategoriaQuandoCategoriaPaiNaoExistir() throws Exception {
        var ACCESS_TOKEN = obtainAccessToken();

        CategoryRequest newCategoryRequest = new CategoryRequest(CATEGORY_NAME);
        newCategoryRequest.setParentId(1L);

        mockMvc
                .perform(post("/api/v1/categories")
                        .header("Authorization", "Bearer " + ACCESS_TOKEN)
                        .contentType("application/json;charset=UTF-8")
                        .content(objectMapper.writeValueAsString(newCategoryRequest)))
                .andExpect(status().isNotFound());
    }

    private String obtainAccessToken() throws Exception {
        User user = new User("alguem@email.com", "123456");
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(user.getLogin(), "123456");
        ResultActions result
                = mockMvc.perform(post("/api/auth")
                .content(objectMapper.writeValueAsString(loginRequest))
                .accept("application/json;charset=UTF-8")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(resultString, LoginResponse.class);

        return loginResponse.getAccessToken();
    }

}