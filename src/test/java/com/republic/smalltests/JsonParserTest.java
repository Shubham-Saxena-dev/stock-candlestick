package com.republic.smalltests;

/*
 * Small test cases for JsonParser types i.e. InstrumentParser and QuoteParser
 * */

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.republic.client.CandlestickWebSocket;
import com.republic.client.IWebSocket;
import com.republic.parsers.InstrumentParser;
import com.republic.parsers.QuoteParser;
import com.republic.repo.InstrumentRepository;
import com.republic.repo.QuoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JsonParserTest {

    private String instrumentRawEvent;
    private String quoteRawEvent = "";
    private IWebSocket webSocket;
    @Mock
    private InstrumentRepository instrumentRepository;
    @Mock
    private QuoteRepository quoteRepository;
    private final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private ListAppender<ILoggingEvent> listAppender;

    @Before
    public void setup() {
        instrumentRawEvent = "{\r\n" +
                "  \"data\": {\r\n" +
                "    \"description\": \"instrument raw event desciption\",\r\n" +
                "    \"isin\": \"ABC123\"\r\n" +
                "  },\r\n" +
                "  \"type\": \"ADD\"\r\n" +
                "}";

        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        quoteRawEvent = "{\r\n" +
                "  \"data\": {\r\n" +
                "    \"price\": 999.99,\r\n" +
                "    \"isin\": \"ABC123\"\r\n" +
                "  },\r\n" +
                "  \"type\": \"QUOTE\"\r\n" +
                "}";
    }

    @Test
    public void givenInstrumentParser_whenValidRawEvent_thenParseSuccessfully() {
        //given
        List<ILoggingEvent> logsList = listAppender.list;
        webSocket = new CandlestickWebSocket(instrumentRepository, new InstrumentParser());

        //when
        Assertions.assertDoesNotThrow(() -> webSocket.onReceivingEvent(instrumentRawEvent));

        //then
        assertTrue(logsList.isEmpty());
    }

    @Test
    public void givenInstrumentParser_whenInValidRawEvent_thenThrowException() {
        //given
        List<ILoggingEvent> logsList = listAppender.list;
        webSocket = new CandlestickWebSocket(instrumentRepository, new InstrumentParser());

        //when
        webSocket.onReceivingEvent("Instrument Bad Event");

        //then
        assertFalse(logsList.isEmpty());
        assertTrue(logsList.get(0).getMessage().contains("Failed to parse Instrument event"));
    }

    @Test
    public void givenQuoteParser_whenValidRawEvent_thenParseSuccessfully() {
        //given
        List<ILoggingEvent> logsList = listAppender.list;
        webSocket = new CandlestickWebSocket(quoteRepository, new QuoteParser());

        //when
        Assertions.assertDoesNotThrow(() -> webSocket.onReceivingEvent(quoteRawEvent));

        //then
        assertTrue(logsList.isEmpty());
    }

    @Test
    public void givenQuoteParser_whenInValidRawEvent_thenThrowException() {
        //given
        List<ILoggingEvent> logsList = listAppender.list;
        webSocket = new CandlestickWebSocket(quoteRepository, new QuoteParser());

        //when
        webSocket.onReceivingEvent("Quote Bad Event");

        //then
        assertFalse(logsList.isEmpty());
        assertTrue(logsList.get(0).getMessage().contains("Failed to parse Quote event"));
    }
}
