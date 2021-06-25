package main.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import main.entity.fromAPI.APIAsset;
import main.service.CryptowatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CryptoController {

    private CryptowatService cryptowatService;

    @GetMapping("/getAssets")
    @ApiOperation("Pobierz wszystkie assety")
    public List<APIAsset> getAssets(){
        return cryptowatService.getAssets().getResult();
    }
}
