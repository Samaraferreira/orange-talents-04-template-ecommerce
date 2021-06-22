package br.com.zup.edu.ecommerce.shared.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Validated LoginRequest request){
        UsernamePasswordAuthenticationToken authenticationToken = request.build();

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            String jwt = tokenManager.generateToken(authentication);

            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}