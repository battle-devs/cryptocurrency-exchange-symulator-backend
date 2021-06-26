package main.entity.CoinMarket;

import lombok.Data;

@Data
public class CoinLatestSummary {
    private Integer id;
    private String name;
    private String symbol;
    private Quote quote;

}
