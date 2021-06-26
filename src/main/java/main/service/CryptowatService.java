package main.service;

import main.model.PriceRequest;
import main.model.AssetList;

import java.math.BigDecimal;

public interface CryptowatService {
    AssetList getAssets();
    BigDecimal getPrice(PriceRequest priceRequest);
}
