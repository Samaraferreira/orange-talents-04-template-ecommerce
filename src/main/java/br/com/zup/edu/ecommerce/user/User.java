package br.com.zup.edu.ecommerce.user;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private @NotBlank @Email String login;

    @Column(nullable = false)
    private @NotBlank @Length(min = 6) String password;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * @deprecated for framework use only
     * */
    @Deprecated
    public User() {
    }

    /** design by contract
     * @param login must be a well-formed email address
     * @param password must be a simple string
     */
    public User(@NotBlank @Email String login, @NotBlank @Length(min = 6) String password) {
        this.login = login;
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
