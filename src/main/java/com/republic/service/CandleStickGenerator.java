package com.republic.service;

/*
 * This class generates candlesticks
 * */

import com.republic.model.CandleStick;
import com.republic.model.Quote;
import com.republic.repo.InstrumentRepository;
import com.republic.repo.QuoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

@Service
public class CandleStickGenerator {

    @Qualifier("instrumentRepo")
    private InstrumentRepository instrumentRepo;
    @Qualifier("quoteRepo")
    private QuoteRepository quoteRepo;

    public CandleStickGenerator(InstrumentRepository instrumentRepo, QuoteRepository quoteRepo) {
        this.instrumentRepo = instrumentRepo;
        this.quoteRepo = quoteRepo;
    }

    //If we have respective isin in instrument cache then generate candlestick
    public List<CandleStick> createCandleSticks(String isin) {

        if (Objects.nonNull(instrumentRepo.findById(isin))) {
            Instant pastThirtyMinutes = Instant.now().minus(30, ChronoUnit.MINUTES);
            return generateCandleSticks(quoteRepo.findLatestQuotes(isin, pastThirtyMinutes), pastThirtyMinutes);
        }
        return Collections.EMPTY_LIST;
    }

    /*
     * For each quote, create a candlestick and update the candlestick cache
     * if cache has candlestick for that key, then compare and update the timestamp and prices.
     * */

    private List<CandleStick> generateCandleSticks(List<Quote> latestQuotes, Instant pastThirtyMinutes) {
        var cache = new TreeMap<Long, CandleStick>();

        latestQuotes.forEach(quote -> {
            long key = Duration.between(pastThirtyMinutes, quote.getTimestamp()).toMinutes();
            CandleStick candleStick = this.buildCandleSticks(quote);
            cache.putIfAbsent(key, candleStick);
            if (cache.containsKey(key)) {
                CandleStick stick = cache.get(key);
                CandleStick.Builder newStick = this.updatePriceAndTimeStamp(stick, candleStick);
                cache.put(key, newStick.build());
            }
        });
        return new ArrayList<>(cache.values());
    }


    private CandleStick.Builder updatePriceAndTimeStamp(CandleStick oldStick, CandleStick candleStick) {
        CandleStick.Builder newStick = new CandleStick.Builder().isin(oldStick.getIsin());

        // update with lowest price
        if (oldStick.getLowPrice().compareTo(candleStick.getLowPrice()) < 0) {
            newStick.lowPrice(oldStick.getLowPrice());
        } else {
            newStick.lowPrice(candleStick.getLowPrice());
        }

        //update with highest price
        if (oldStick.getHighPrice().compareTo(candleStick.getHighPrice()) > 0) {
            newStick.highPrice(oldStick.getHighPrice());
        } else {
            newStick.highPrice(candleStick.getHighPrice());
        }

        //update with maximum timestamp
        if (oldStick.getOpenTimestamp().compareTo(candleStick.getOpenTimestamp()) > 0) {
            newStick.openPrice(oldStick.getHighPrice());
            newStick.openTimestamp(oldStick.getOpenTimestamp());
        } else {
            newStick.openPrice(candleStick.getHighPrice());
            newStick.openTimestamp(candleStick.getOpenTimestamp());
        }

        //update with lowest timestamp
        if (oldStick.getCloseTimestamp().compareTo(candleStick.getCloseTimestamp()) < 0) {
            newStick.closedPrice(oldStick.getClosedPrice());
            newStick.closeTimestamp(oldStick.getCloseTimestamp());
        } else {
            newStick.closedPrice(candleStick.getClosedPrice());
            newStick.closeTimestamp(candleStick.getCloseTimestamp());
        }

        return newStick;
    }

    // build a new candlestick
    private CandleStick buildCandleSticks(Quote quote) {
        return new CandleStick.Builder().isin(quote.getIsin())
                .openPrice(quote.getPrice())
                .closedPrice(quote.getPrice())
                .openTimestamp(quote.getTimestamp())
                .closeTimestamp(quote.getTimestamp())
                .highPrice(quote.getPrice())
                .lowPrice(quote.getPrice())
                .openPrice(quote.getPrice())
                .closedPrice(quote.getPrice())
                .build();
    }
}
