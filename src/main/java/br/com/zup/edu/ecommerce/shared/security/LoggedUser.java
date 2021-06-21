package br.com.zup.edu.ecommerce.shared.security;

import java.util.List;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoggedUser implements UserDetails {

    private br.com.zup.edu.ecommerce.user.User user;
    private User springUserDetails;

    public LoggedUser(@NotNull br.com.zup.edu.ecommerce.user.User user) {
        this.user = user;
        this.springUserDetails = new User(user.getLogin(), user.getPassword(), List.of());
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return springUserDetails.getAuthorities();
    }



    public String getPassword() {
        return springUserDetails.getPassword();
    }

    public String getUsername() {
        return springUserDetails.getUsername();
    }

    public boolean isEnabled() {
        return springUserDetails.isEnabled();
    }

    public boolean isAccountNonExpired() {
        return springUserDetails.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return springUserDetails.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return springUserDetails.isCredentialsNonExpired();
    }

    public br.com.zup.edu.ecommerce.user.User get() {
        return user;
    }
}
