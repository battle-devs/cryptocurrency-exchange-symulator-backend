package main.configuration;

import main.service.UserService;
import main.service.impl.CurrentUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private CurrentUserDetailsService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userServiceImpl;

	public WebSecurityConfig(
			CurrentUserDetailsService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user")
				.permitAll()
				.antMatchers(HttpMethod.POST, "/login")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), userServiceImpl))
				// this disables session creation on Spring Security
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers(
						"/v2/api-docs",
						"/swagger-resources/configuration/ui",
						"/configuration/ui",
						"/swagger-resources",
						"/configuration/security",
						"/swagger-ui.html",
						"/webjars/**");
	}

}
