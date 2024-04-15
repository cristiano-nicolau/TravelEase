package hw.tqs.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import hw.tqs.cache.Cache;
import hw.tqs.services.ExchangeRateService;

@WebMvcTest(ExchangeController.class)
public class ExchangeRateControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @MockBean
    private Cache cache;

    @Test
    @DisplayName("Test get exchange rate")
    void testGetExchangeRate() throws Exception {
        when(exchangeRateService.getExchangeRate("USD", "EUR")).thenReturn(0.85);
        mvc.perform(get("/api/exchange/USD/EUR")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("0.85"));
        verify(exchangeRateService, times(1)).getExchangeRate("USD", "EUR");
    }

}
