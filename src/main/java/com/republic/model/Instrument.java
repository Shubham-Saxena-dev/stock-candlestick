package com.republic.model;

public class Instrument {

    private final String isin;
    private final String description;

    public Instrument(Builder builder) {
        this.isin = builder.isin;
        this.description = builder.description;
    }

    public String getIsin() {
        return isin;
    }

    public String getDescription() {
        return description;
    }

    public static final class Builder {

        private String isin;
        private String description;

        public Builder isin(String isin) {
            this.isin = isin;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Instrument build() {
            return new Instrument(this);
        }
    }
}
