package main.controller;

import lombok.AllArgsConstructor;
import main.entity.CoinMarket.CoinLatestSummary;
import main.entity.fromAPI.APIAsset;
import main.services.CoinMarketCapClient;
import main.services.CryptowatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CryptoController {
    private CryptowatClient cryptowatClient;
    private CoinMarketCapClient coinMarketCapClient;


    @GetMapping("/getAssets")
    public List<APIAsset> getAssets(){
        return cryptowatClient.getAssets().getResult();
    }

    @GetMapping("/getTop100byVolume")
    public List<CoinLatestSummary> getTop100byVolume() {
        return coinMarketCapClient.getTop100byVolume().getData();
    }


}
