package com.republic.client;

/*
 * WebSocket class for receiving raw events and parsing them into
 * respective Instrument or Quote events.
 * This class takes Instrument or Quote  repository and parser that are
 * configured by config beans
 * */

import com.republic.parsers.JsonParser;
import com.republic.repo.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

@ClientEndpoint
public class CandlestickWebSocket implements IWebSocket {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final IRepository repo;
    private final JsonParser parser;

    public CandlestickWebSocket(IRepository repo, JsonParser parser) {
        this.repo = repo;
        this.parser = parser;
    }

    //To receive instrument or quote raw events from partner service
    @Override
    @OnMessage
    public void onReceivingEvent(String message) {
        try {
            parser.parseJsonAndStoreInCache(message, repo);
        } catch (Exception ex) {
            LOG.error("Exception: {} occurred for message {}", ex, message);
        }
    }

}
