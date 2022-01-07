package com.republic.event;

import com.republic.model.Instrument;
import com.republic.parsers.IEvent;

public class InstrumentEvent implements IEvent<Instrument> {

    private Type type;

    private Instrument data;

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Instrument getData() {
        return data;
    }

    public void type(Type type) {
        this.type = type;
    }

    public void entity(Instrument data) {
        this.data = data;
    }

}
