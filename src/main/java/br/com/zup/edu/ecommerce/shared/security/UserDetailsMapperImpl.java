package br.com.zup.edu.ecommerce.shared.security;

import br.com.zup.edu.ecommerce.user.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class UserDetailsMapperImpl implements UserDetailsMapper {

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new LoggedUser((User) shouldBeASystemUser);
    }
}
