package main.service;

import java.math.BigDecimal;
import java.util.Map;

import main.dto.AssetList;

public interface CryptowatService {
    AssetList getAssets();
    BigDecimal getPrice(String currency);
    Map<String, BigDecimal> getCurrentPrices();
}
