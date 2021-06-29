package main.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import main.dto.AssetList;
import main.dto.PriceRequestResult;
import main.dto.PriceResult;
import main.service.CryptowatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptowatServiceImpl implements CryptowatService {

    RestTemplate restTemplate;

    public static Map<String, String> currencies;

    static {
        currencies = new HashMap<>();
        currencies.putAll(Map.of("Bitcoin", "btcpln",
                "Bitcoin Gold", "btgpln",
                "Ethereum", "ethpln",
                "Lisk", "lskpln",
                "Litecoin", "ltcpln",
                "Game Credits", "gamepln",
                "Dash", "dashpln",
                "OMG Network", "omgpln"));
    }

    public CryptowatServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AssetList getAssets() {
        String url = "https://api.cryptowat.ch/assets";
        ResponseEntity<AssetList> response = restTemplate.getForEntity(url, AssetList.class);
        return response.getBody();
    }

    @Override
    public BigDecimal getPrice(String currency) {
        String url = String.format("https://api.cryptowat.ch/markets/bitbay/%s/price", currency);
        ResponseEntity<PriceRequestResult> response = restTemplate.getForEntity(url, PriceRequestResult.class);
        return Optional.ofNullable(response.getBody())
                .map(PriceRequestResult::getResult)
                .map(PriceResult::getPrice)
                .orElse(null);
    }

    @Override
    public Map<String, BigDecimal> getCurrentPrices() {

        Map<String, BigDecimal> currenciesResponse = new HashMap<>();

        Object o = currencies.keySet()
                .stream()
                .map(key -> {
                    ResponseEntity<PriceRequestResult> response =
                            restTemplate.getForEntity("https://api.cryptowat.ch/markets/bitbay/" + currencies.get(key) + "/price", PriceRequestResult.class);
                    currenciesResponse.put(key, Optional.ofNullable(response.getBody())
                            .map(PriceRequestResult::getResult)
                            .map(PriceResult::getPrice)
                            .orElse(null));
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return currenciesResponse;
    }

}

