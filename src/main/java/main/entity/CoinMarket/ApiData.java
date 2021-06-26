package main.entity.CoinMarket;

import lombok.Data;

import java.util.List;

@Data
public class ApiData {
    private List<CoinLatestSummary> data;
}
