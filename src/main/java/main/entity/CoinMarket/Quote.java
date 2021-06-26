package main.entity.CoinMarket;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Quote {
    private BigDecimal price;
    private BigDecimal volume_24h;
    private Double percent_change_1h;
    private Double percent_change_24h;
    private Double percent_change_7d;
    private Double market_cap;
}
