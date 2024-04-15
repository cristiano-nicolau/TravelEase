package hw.tqs.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hw.tqs.cache.Cache;
import hw.tqs.http.HttpClient;
import hw.tqs.services.ExchangeRateService;

class ExchangeRateServiceTest {

    private HttpClient httpClient;
    private Cache cache;
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    public void setUp() {
        httpClient = mock(HttpClient.class);
        cache = mock(Cache.class);
        exchangeRateService = new ExchangeRateService(httpClient, cache);
    }

    @Test
    @DisplayName("Test get exchange rate - Cache Hit")
    void testGetExchangeRate_CacheHit() throws IOException, ParseException {
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        double exchangeRate = 1.2;

        when(cache.getFromCache(baseCurrency + targetCurrency)).thenReturn(exchangeRate);

        double result = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);

        assertEquals(exchangeRate, result);
        verify(cache, times(1)).getFromCache(baseCurrency + targetCurrency);
        verifyNoInteractions(httpClient);
    }

    @Test
    @DisplayName("Test get exchange rate - Cache Miss")
    void testGetExchangeRate_CacheMiss() throws IOException, ParseException {
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        double exchangeRate = 1.2;

        when(cache.getFromCache(baseCurrency + targetCurrency)).thenReturn(null);
        when(httpClient.doHttpGet(anyString())).thenReturn("{\"data\":{\"EUR\":1.2}}");
        doNothing().when(cache).addToCache(anyString(), anyDouble());

        double result = exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);

        assertEquals(exchangeRate, result);
        verify(cache, times(1)).getFromCache(baseCurrency + targetCurrency);
        verify(httpClient, times(1)).doHttpGet(anyString());
        verify(cache, times(1)).addToCache(baseCurrency + targetCurrency, exchangeRate);
    }
}
