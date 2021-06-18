package main.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import main.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthenticationHelper {

    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication attemptAuthentication(
            Credentials authUser, Collection<? extends GrantedAuthority> collection)
            throws AuthenticationException {
        final Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authUser.getUsername(), authUser.getPassword(), collection));

        // Inject into security context
        return authentication;
    }

    public String generateToken(Authentication auth) {
        StringBuilder resToken = new StringBuilder();
        String username =
                ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        resToken.append(
                ((org.springframework.security.core.userdetails.User) auth.getPrincipal())
                        .getAuthorities());
        Map<String, Object> roles = new HashMap<>();
        roles.put("USER", username);
        roles.put("ROLE", resToken);
        String token =
                Jwts.builder()
                        .setSubject(username)
                        .setClaims(roles)
                        .setExpiration(
                                new Date(
                                        System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME.longValue()))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.value())
                        .compact();
        return token;
    }
}
