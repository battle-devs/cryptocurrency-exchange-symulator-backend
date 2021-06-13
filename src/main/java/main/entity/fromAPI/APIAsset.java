package main.entity.fromAPI;

import lombok.Data;

@Data
public class APIAsset {
    private Integer id;
    private String symbol;
    private String name;
    private Boolean fiat;
}
