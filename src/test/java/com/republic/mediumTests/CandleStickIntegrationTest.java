package com.republic.mediumTests;

import com.republic.CandleStickApplication;
import com.republic.event.Type;
import com.republic.model.CandleStick;
import com.republic.model.Instrument;
import com.republic.model.Quote;
import com.republic.repo.InstrumentRepository;
import com.republic.repo.QuoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CandleStickApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandleStickIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private QuoteRepository quoteRepository;
    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        this.prepareInstrumentData();
        this.prepareQuoteData();
    }

    private void prepareInstrumentData() {
        Instrument instrumentEvent = new Instrument.Builder().description("trade republic data").isin("ABC123").build();
        instrumentRepository.storeInMemory(Type.ADD, instrumentEvent);
    }

    private void prepareQuoteData() {
        Quote quoteEvent1 = new Quote();

        quoteEvent1.timestamp(Instant.now());
        quoteEvent1.isin("ABC123");
        quoteEvent1.price(new BigDecimal(999.99));

        quoteRepository.storeInMemory(Type.QUOTE, quoteEvent1);

        Quote quoteEvent2 = new Quote();
        quoteEvent2.timestamp(Instant.now());
        quoteEvent2.isin("ABC123");
        quoteEvent2.price(new BigDecimal(111.11));

        quoteRepository.storeInMemory(Type.QUOTE, quoteEvent2);

    }

    @Test
    public void givenCandleStick_whenCorrectDataProvided_thenSuccess() throws URISyntaxException {
        //given
        URI uri = new URI("http://localhost:" + port + "/candlestick?isin=ABC123");

        //when
        ResponseEntity<Object> response = this.restTemplate.getForEntity(uri, Object.class);

        //then
        List<CandleStick> result = (List<CandleStick>) response.getBody();
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void givenCandleStick_whenISINisNotAvailable_thenFailed() throws URISyntaxException {
        //given
        URI uri = new URI("http://localhost:" + port + "/candlestick?isin=DEF456");

        //when
        ResponseEntity<Object> response = this.restTemplate.getForEntity(uri, Object.class);

        //then
        List<CandleStick> result = (List<CandleStick>) response.getBody();
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(result.size(), 0);
    }

}
