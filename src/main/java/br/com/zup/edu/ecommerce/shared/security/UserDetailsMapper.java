package br.com.zup.edu.ecommerce.shared.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsMapper {

    /**
     *
     * @param shouldBeASystemUser um objeto que deveria representar seu usuário logado
     */
    UserDetails map(Object shouldBeASystemUser);
}
