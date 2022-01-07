package com.republic.parsers;

/*
 * This class converts the raw message received from websocket
 * into quote event.
 * */

import com.google.gson.JsonSyntaxException;
import com.republic.event.QuoteEvent;
import com.republic.utils.GsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class QuoteParser extends JsonParser {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public QuoteEvent parseJson(String message) {
        try {
            QuoteEvent event = GsonMapper.getInstance().fromJson(message, QuoteEvent.class);
            event.getData().timestamp(Instant.now());
            return event;
        } catch (JsonSyntaxException ex) {
            LOG.error("Failed to parse Quote event with error {}", ex.getMessage());
            throw ex;
        }
    }

}
