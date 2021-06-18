package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.configuration.JWTAuthenticationHelper;
import main.service.impl.CryptowatServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@SpringBootApplication
public class CryptoApp {
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CryptoApp.class, args);
//		CryptowatServiceImpl cryptowatClient = new CryptowatServiceImpl();
//		var bub = cryptowatClient.getAssets();
//		System.out.println(new ObjectMapper().writeValueAsString(bub));
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JWTAuthenticationHelper jwtAuthenticationHelper() {
		return new JWTAuthenticationHelper();
	}

}
