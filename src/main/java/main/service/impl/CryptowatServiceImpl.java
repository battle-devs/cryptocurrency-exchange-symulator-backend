package main.service.impl;

import main.entity.fromAPI.Result;
import main.service.CryptowatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptowatServiceImpl implements CryptowatService {

    @Override
    public Result getAssets(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.cryptowat.ch/assets";
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class);
        return response.getBody();
    }
}

