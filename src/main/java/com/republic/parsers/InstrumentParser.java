package com.republic.parsers;

/*
 * This class converts the raw message received from websocket
 * into Instrument event.
 * */

import com.google.gson.JsonSyntaxException;
import com.republic.event.InstrumentEvent;
import com.republic.utils.GsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstrumentParser extends JsonParser {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public InstrumentEvent parseJson(String message) {
        try {
            return GsonMapper.getInstance().fromJson(message, InstrumentEvent.class);
        } catch (JsonSyntaxException ex) {
            LOG.error("Failed to parse Instrument event with error {}", ex.getMessage());
            throw ex;
        }
    }
}
