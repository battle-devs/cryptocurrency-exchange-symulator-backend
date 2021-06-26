package main.services;

import main.entity.CoinMarket.ApiData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CoinMarketCapClient {
    public ApiData getTop100byVolume(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        ResponseEntity<ApiData> response = restTemplate.getForEntity(url, ApiData.class);
        return response.getBody();
    }
}
