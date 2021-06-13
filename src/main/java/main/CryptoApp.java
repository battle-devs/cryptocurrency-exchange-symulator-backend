package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.services.CryptowatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CryptoApp {
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CryptoApp.class, args);
		CryptowatClient cryptowatClient = new CryptowatClient();
		var bub = cryptowatClient.getAssets();
		System.out.println(new ObjectMapper().writeValueAsString(bub));
	}
}
