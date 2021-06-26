package main.service;

import java.math.BigDecimal;

import main.model.AssetList;
import main.model.PriceRequest;

public interface CryptowatService {
    AssetList getAssets();
    BigDecimal getPrice(String currency);
}
