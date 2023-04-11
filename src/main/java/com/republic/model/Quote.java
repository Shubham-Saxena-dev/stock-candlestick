package com.republic.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Quote {

    private BigDecimal price;
    private Instant timestamp;
    private String isin;


    public BigDecimal getPrice() {
        return price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getIsin() {
        return isin;
    }

    public void price(BigDecimal price) {
        this.price = price;
    }

    public void timestamp(Instant timestamp) {
        this.timestamp = timestamp == null ? Instant.now() : timestamp;
    }

    public void isin(String isin) {
        this.isin = isin;
    }
}
