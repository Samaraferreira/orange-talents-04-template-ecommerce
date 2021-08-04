package br.com.zup.edu.ecommerce.user;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Users")
@RestController
@RequestMapping(value = "api/v1/users", produces="application/json", consumes="application/json")
public class UserController {

    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    private ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        User user = request.toModel();

        repository.save(user);

        return ResponseEntity.ok().build();
    }
}
