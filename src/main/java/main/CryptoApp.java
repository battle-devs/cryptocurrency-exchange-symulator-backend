package main;

import main.configuration.JWTAuthenticationHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@SpringBootApplication
public class CryptoApp {
	public static void main(String[] args) {
		SpringApplication.run(CryptoApp.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JWTAuthenticationHelper jwtAuthenticationHelper() {
		return new JWTAuthenticationHelper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
