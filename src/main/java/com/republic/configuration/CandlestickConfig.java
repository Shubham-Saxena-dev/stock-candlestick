package com.republic.configuration;

/*
 * Configuration class for different beans to be managed by
 * spring application context
 * */

import com.republic.client.CandlestickWebSocket;
import com.republic.client.IWebSocket;
import com.republic.event.InstrumentEvent;
import com.republic.event.QuoteEvent;
import com.republic.parsers.InstrumentParser;
import com.republic.parsers.JsonParser;
import com.republic.parsers.QuoteParser;
import com.republic.repo.InstrumentRepository;
import com.republic.repo.QuoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandlestickConfig {

    @Bean("instrumentWebClientBean")
    public IWebSocket getInstrumentWebClientBean(
            @Qualifier("instrumentRepo") InstrumentRepository repo, @Qualifier("instrumentParser") JsonParser<InstrumentEvent> parser) {
        return new CandlestickWebSocket(repo, parser);
    }

    @Bean("quoteWebClientBean")
    public IWebSocket getQuoteWebClientBean(
            @Qualifier("quoteRepo") QuoteRepository repo,
            @Qualifier("quoteParser") JsonParser<QuoteEvent> parser) {
        return new CandlestickWebSocket(repo, parser);
    }

    @Bean("instrumentRepo")
    public InstrumentRepository getInstrumentRepo() {
        return new InstrumentRepository();
    }

    @Bean("quoteRepo")
    public QuoteRepository getQuoteRepo() {
        return new QuoteRepository();
    }

    @Bean("instrumentParser")
    public InstrumentParser getInstrumentParser() {
        return new InstrumentParser();
    }

    @Bean("quoteParser")
    public QuoteParser getQuoteParser() {
        return new QuoteParser();
    }

}
