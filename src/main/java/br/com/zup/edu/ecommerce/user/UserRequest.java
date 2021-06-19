package br.com.zup.edu.ecommerce.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UserRequest {

    private @NotBlank @Email String login;
    private @NotBlank @Length(min = 6) String password;

    public UserRequest(@NotBlank @Email String login, @NotBlank @Length(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public User toModel() {
        return new User(login, password);
    }
}
