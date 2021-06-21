package br.com.zup.edu.ecommerce.shared.security;

import br.com.zup.edu.ecommerce.user.User;
import br.com.zup.edu.ecommerce.user.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return userDetailsMapper.map(user.get());
    }

}
