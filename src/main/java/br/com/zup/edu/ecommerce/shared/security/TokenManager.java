package br.com.zup.edu.ecommerce.shared.security;


import br.com.zup.edu.ecommerce.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    @Value("${ecommerce.jwt.expiration}")
    private String expiration;

    @Value("${ecommerce.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authenticate) {
        //Buscamos o usuario autenticado
        LoggedUser logado = (LoggedUser) authenticate.getPrincipal();
        
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API DO Mercado Livre Orange Talents")
                .setSubject(logado.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                //algoritimo de encriptação
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserName(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }
}