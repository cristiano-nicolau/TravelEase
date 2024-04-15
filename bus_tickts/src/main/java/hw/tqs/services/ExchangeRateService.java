package hw.tqs.services;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import hw.tqs.cache.Cache;
import hw.tqs.http.HttpClient;

@Service
public class ExchangeRateService {

    private final HttpClient httpClient;
    private final Cache cache;

    private static final Logger logger = LogManager.getLogger(ExchangeRateService.class);

    private final String apiUrl = "https://api.freecurrencyapi.com/v1/latest";
    private final String code = "fca_live_fl0HmrwcSrZVVUYsQP0sIGIZYpI6WJGuaF7nqCHt";

    public ExchangeRateService(HttpClient httpClient, Cache cache) {
        this.httpClient = httpClient;
        this.cache = cache;
    }

    public double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException, ParseException {
        String cacheKey = baseCurrency + targetCurrency;

        logger.info("Getting exchange rate from " + baseCurrency + " to " + targetCurrency);

        Double exchangeRate = cache.getFromCache(cacheKey);

        if (exchangeRate != null) {
            logger.info("Cache hit for " + baseCurrency + " to " + targetCurrency + " exchange rate value: " + exchangeRate);
            return exchangeRate;
        } else {
            logger.info("Cache miss for " + baseCurrency + " to " + targetCurrency + " exchange rate. Calling API...");
            exchangeRate = callApi(baseCurrency, targetCurrency);
            cache.addToCache(cacheKey, exchangeRate);
            cache.cacheTimer(cacheKey, 900000);

            logger.info("Cache updated with " + baseCurrency + " to " + targetCurrency + " exchange rate value: " + exchangeRate + " for 15 minutes");

            return exchangeRate;
        }
    }

    private Double callApi(String baseCurrency, String targetCurrency) throws IOException, ParseException {
        String requestUrl = apiUrl + "?apikey=" + code + "&currencies=" + targetCurrency + "&base_currency=" + baseCurrency;
        String response = httpClient.doHttpGet(requestUrl);
        return parseExchangeRateFromResponse(response, targetCurrency);
    }

    public double parseExchangeRateFromResponse(String response, String targetCurrency) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        JSONObject data = (JSONObject) jsonObject.get("data");
        Double exchangeRate = (Double) data.get(targetCurrency);

        return exchangeRate != null ? exchangeRate : 0.0;
    }

}
