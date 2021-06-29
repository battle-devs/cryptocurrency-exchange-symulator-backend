package main.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import main.dto.APIAsset;
import main.service.CryptowatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CryptoController {

    private CryptowatService cryptowatService;

    @GetMapping("/getAssets")
    public List<APIAsset> getAssets() {
        return cryptowatService.getAssets().getResult();
    }

    @GetMapping("/getPrice/{currency}")
    @ApiOperation("Pobiera kurs waluty")
    public BigDecimal getPrice(@PathVariable String currency) {
        return cryptowatService.getPrice(currency);
    }


    @GetMapping("/getPrices")
    @ApiOperation("Pobiera kurs waluty")
    public Map<String, BigDecimal> getPrice() {
        return cryptowatService.getCurrentPrices();
    }

}
