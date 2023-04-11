package com.republic.model;

import java.math.BigDecimal;
import java.time.Instant;

public class CandleStick {

    private final String isin;
    private final Instant openTimestamp;
    private final Instant closeTimestamp;
    private final BigDecimal highPrice;
    private final BigDecimal lowPrice;
    private final BigDecimal openPrice;
    private final BigDecimal closedPrice;

    public CandleStick(Builder builder) {
        this.isin = builder.isin;
        this.openTimestamp = builder.openTimestamp;
        this.closeTimestamp = builder.closeTimestamp;
        this.highPrice = builder.highPrice;
        this.lowPrice = builder.lowPrice;
        this.openPrice = builder.openPrice;
        this.closedPrice = builder.closedPrice;
    }

    public String getIsin() {
        return isin;
    }

    public Instant getOpenTimestamp() {
        return openTimestamp;
    }

    public Instant getCloseTimestamp() {
        return closeTimestamp;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getClosedPrice() {
        return closedPrice;
    }

    public static final class Builder {

        private String isin;
        private Instant openTimestamp;
        private Instant closeTimestamp;
        private BigDecimal highPrice;
        private BigDecimal lowPrice;
        private BigDecimal openPrice;
        private BigDecimal closedPrice;

        public Builder isin(String isin) {
            this.isin = isin;
            return this;
        }

        public Builder openTimestamp(Instant openTimestamp) {
            this.openTimestamp = openTimestamp;
            return this;
        }

        public Builder closeTimestamp(Instant closeTimestamp) {
            this.closeTimestamp = closeTimestamp;
            return this;
        }

        public Builder highPrice(BigDecimal highPrice) {
            this.highPrice = highPrice;
            return this;
        }

        public Builder lowPrice(BigDecimal lowPrice) {
            this.lowPrice = lowPrice;
            return this;
        }

        public Builder openPrice(BigDecimal openPrice) {
            this.openPrice = openPrice;
            return this;
        }

        public Builder closedPrice(BigDecimal closedPrice) {
            this.closedPrice = closedPrice;
            return this;
        }

        public CandleStick build() {
            return new CandleStick(this);
        }
    }
}
