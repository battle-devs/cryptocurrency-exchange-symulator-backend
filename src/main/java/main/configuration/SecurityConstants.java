package main.configuration;

public enum SecurityConstants {
    EXPIRATION_TIME("", 86_400_000),
    SECRET("ThisIsASecret", 0),
    TOKEN_PREFIX("Bearer ", 0),
    HEADER_STRING("Authorization", 0);

    private String value;
    private long longValue;

     SecurityConstants(String value, long longValue) {
        this.value = value;
        this.longValue = longValue;
    }

    public String value() {
        return value;
    }

    public long longValue() {
        return longValue;
    }
}

