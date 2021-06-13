package main.entity.fromAPI;

public class APIAsset {
    private String id;
    private String symbol;
    private String name;
    private Boolean fiat;

    public APIAsset(String id, String symbol, String name, Boolean fiat) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.fiat = fiat;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public Boolean getFiat() {
        return fiat;
    }
}
