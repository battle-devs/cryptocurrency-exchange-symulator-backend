package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.service.impl.CryptowatServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoApp {
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CryptoApp.class, args);
		CryptowatServiceImpl cryptowatClient = new CryptowatServiceImpl();
		var bub = cryptowatClient.getAssets();
		System.out.println(new ObjectMapper().writeValueAsString(bub));
	}
}
