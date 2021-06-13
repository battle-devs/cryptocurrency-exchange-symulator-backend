package main.services;

import main.entity.fromAPI.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class CryptowatClient implements IRestClient{
    Map<String,String> map;

    @Override
    public Result getAssets(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.cryptowat.ch/assets";
        ResponseEntity<Result> response = restTemplate.getForEntity(url, Result.class);
        return response.getBody();
    }
}
//
