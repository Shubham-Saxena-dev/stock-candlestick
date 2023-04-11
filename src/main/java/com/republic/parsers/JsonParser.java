package com.republic.parsers;

import com.republic.repo.IRepository;

public abstract class JsonParser<E> {

    public abstract IEvent<E> parseJson(String message);

    public void parseJsonAndStoreInCache(String message, IRepository<E> repo) {
        IEvent<E> event = this.parseJson(message);
        repo.storeInMemory(event.getType(), event.getData());
    }
}
