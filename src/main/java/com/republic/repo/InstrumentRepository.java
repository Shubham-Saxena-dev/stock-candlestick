package com.republic.repo;

/*
* This class resembles a in memory database by storing/removing the instrument events in map (cache)
* based upon the type.
* */

import com.republic.event.Type;
import com.republic.model.Instrument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstrumentRepository implements IRepository<Instrument> {

    @Autowired
    private QuoteRepository quoteRepo;
    private final Map<String, Instrument> instrumentCache = new ConcurrentHashMap<>();

    public Instrument findById(String id) {
        return instrumentCache.get(id);
    }

    @Override
    public void storeInMemory(Type operation, Instrument entity) {
        if (Type.ADD.equals(operation))
            instrumentCache.put(entity.getIsin(), entity);
        else {
            this.removeEntity(entity.getIsin());
        }
    }

    private void removeEntity(String isin) {
        quoteRepo.deleteQuotes(isin);
        instrumentCache.remove(isin);
    }
}
