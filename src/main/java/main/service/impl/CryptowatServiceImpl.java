package main.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import main.model.AssetList;
import main.model.PriceRequest;
import main.model.PriceRequestResult;
import main.model.PriceResult;
import main.service.CryptowatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptowatServiceImpl implements CryptowatService {

    RestTemplate restTemplate;

    public CryptowatServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AssetList getAssets(){
        String url = "https://api.cryptowat.ch/assets";
        ResponseEntity<AssetList> response = restTemplate.getForEntity(url, AssetList.class);
        return response.getBody();
    }

    @Override
    public BigDecimal getPrice(String currency) {
        String url = String.format("https://api.cryptowat.ch/markets/binance-us/%s/price", currency);
        ResponseEntity<PriceRequestResult> response = restTemplate.getForEntity(url, PriceRequestResult.class);
        return Optional.ofNullable(response.getBody())
                .map(PriceRequestResult::getResult)
                .map(PriceResult::getPrice)
                .orElse(null);
    }

}

