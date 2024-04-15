package hw.tqs.controller;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hw.tqs.services.ExchangeRateService;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/{baseCurrency}/{targetCurrency}")
    public double getExchangeRate(@PathVariable String baseCurrency, @PathVariable String targetCurrency) throws IOException, ParseException {
        return exchangeRateService.getExchangeRate(baseCurrency, targetCurrency);
    }
}