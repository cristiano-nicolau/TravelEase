package hw.tqs.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw.tqs.http.HttpClient;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalApiConnectionTest {

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = new HttpClient();
    }
    
    @Test 
    void testExternalApiConnection() throws IOException, ParseException {
        assertThat(httpClient.doHttpGet("https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_fl0HmrwcSrZVVUYsQP0sIGIZYpI6WJGuaF7nqCHt&base_currency=USD&currencies=EUR")).isNotNull();
    }

    @Test
    void testWhenRequestWithInvalidURL() {
        assertThrows(IOException.class, () -> httpClient.doHttpGet("qfefeqrbwgr"));
    }

}
