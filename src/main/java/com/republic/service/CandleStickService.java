package com.republic.service;

/*
 * This class receives request and create connection with websocket partner service
 * */

import com.republic.client.IWebSocket;
import com.republic.model.CandleStick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.List;

@Service
public class CandleStickService {

    @Autowired
    @Qualifier("quoteWebClientBean")
    private IWebSocket quoteWebSocket;
    private CandleStickGenerator generator;
    @Autowired
    @Qualifier("instrumentWebClientBean")
    private IWebSocket instrumentWebSocket;
    private String instrumentPathUri;
    private String quotePathUri;

    public CandleStickService(@Value("${instrumentPathUrl}") String instrumentPathUri,
                              @Value("${quotesPathUrl}") String quotePathUri,
                              CandleStickGenerator generator) {
        this.instrumentPathUri = instrumentPathUri;
        this.quotePathUri = quotePathUri;
        this.generator = generator;
    }

    public void connectToWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(instrumentWebSocket, new URI(instrumentPathUri));
            container.connectToServer(quoteWebSocket, new URI(quotePathUri));
        } catch (Exception ex) {
            System.out.println(ex); //remember to replace it with log
        }
    }

    public List<CandleStick> computeCandleSticks(String isin) {
        this.connectToWebSocket();
        return generator.createCandleSticks(isin);
    }
}
