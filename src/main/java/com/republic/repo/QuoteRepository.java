package com.republic.repo;

/*
 * This class resembles a in memory database by storing/removing the quote events in map (cache)
 * based upon the type.
 * */

import com.republic.event.Type;
import com.republic.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class QuoteRepository implements IRepository<Quote> {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final Map<String, ConcurrentSkipListMap<Long, Quote>> quoteCache = new ConcurrentHashMap<>();

    /*
     * if cache does not have isin, add pair
     * else update the respective isin key map to new entity
     * and also update the cache to last 30 minutes
     * */

    @Override
    public void storeInMemory(Type type, Quote entity) {
        if (type != Type.QUOTE) {
            return;
        }
        LOG.info("Quote details:" + entity.getIsin() + ",with Price: " + entity.getPrice());
        var isin = entity.getIsin();
        if (!quoteCache.containsKey(isin)) {
            quoteCache.put(isin, new ConcurrentSkipListMap<>());
        }
        var quotesDetails = quoteCache.get(isin);
        quotesDetails.put(entity.getTimestamp().toEpochMilli(), entity);
        this.updateCache(quotesDetails);
    }

    public void deleteQuotes(String isin) {
        quoteCache.remove(isin);
    }

    // to clear values less than the given key
    private void updateCache(ConcurrentSkipListMap<Long, Quote> quotesDetails) {
        var pastThirtyMinutes = Instant.now().minus(30, ChronoUnit.MINUTES).toEpochMilli();
        var headMap = quotesDetails.headMap(pastThirtyMinutes);
        headMap.clear();
    }

    public List<Quote> findLatestQuotes(String isin, Instant time) {
        if (!quoteCache.containsKey(isin))
            return Collections.EMPTY_LIST;
        return new ArrayList<>(quoteCache.get(isin).tailMap(time.toEpochMilli()).values());
    }
}
