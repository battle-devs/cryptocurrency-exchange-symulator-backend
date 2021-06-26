package main.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceRequest {
    private String exchangeSymbol;
    private String pairSymbol;
}
