package com.republic.event;

import com.republic.model.Quote;
import com.republic.parsers.IEvent;

public class QuoteEvent implements IEvent<Quote> {

    private Type type;
    private Quote data;

    public void entity(Quote data) {
        this.data = data;
    }

    public void type(Type type) {
        this.type = type;
    }


    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Quote getData() {
        return data;
    }

}
