package main.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.entity.User;
import main.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private UserService service;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserService userServiceImpl) {
        super(authManager);
        this.service = userServiceImpl;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING.value());

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX.value())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING.value());
        if (token != null) {
            // parse the token.
            Claims user =
                    Jwts.parser()
                            .setSigningKey(SecurityConstants.SECRET.value())
                            .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX.value(), ""))
                            .getBody();

            if (user != null) {
               User current = service.findByUsername(user.getOrDefault("USER", null).toString());
                return new UsernamePasswordAuthenticationToken(user, null, current.getAuthorities());
            }
            return null;
        }
        return null;
    }
}