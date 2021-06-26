package main.controller;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import main.model.APIAsset;
import main.model.PriceRequest;
import main.service.CryptowatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CryptoController {

    private CryptowatService cryptowatService;

    @GetMapping("/getAssets")
    @ApiOperation("Pobierz wszystkie assety")
    public List<APIAsset> getAssets() {
        return cryptowatService.getAssets().getResult();
    }

    @GetMapping("/getPrice")
    @ApiOperation("Pobiera kurs waluty")
    public BigDecimal getPrice(@RequestBody PriceRequest priceRequest) {
        return cryptowatService.getPrice(priceRequest);
    }

}