package main.controller;

import lombok.AllArgsConstructor;
import main.entity.fromAPI.APIAsset;
import main.services.CryptowatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CryptoController {
    private CryptowatClient client;

    @GetMapping("/getAssets")
    public List<APIAsset> getAssets(){
        return client.getAssets().getResult();
    }

}
