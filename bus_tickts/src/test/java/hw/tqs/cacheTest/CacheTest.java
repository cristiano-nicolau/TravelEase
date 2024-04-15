package hw.tqs.cacheTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import hw.tqs.cache.Cache;
import hw.tqs.services.ExchangeRateService;
import hw.tqs.controller.ExchangeController;


public class CacheTest {

    private Cache cache;

    @BeforeEach
    void setUp() {
        cache = new Cache();
    }

    @Test
    @DisplayName("Test add to cache and get from cache")
    void testAddToCacheAndGetFromCache() {
        cache.addToCache("EURUSD", 1.2);
        assertEquals(1.2, cache.getFromCache("EURUSD"));
    }

    @Test
    @DisplayName("Test get from cache with miss")
    void testGetFromCacheWithMiss() {
        assertNull(cache.getFromCache("EURUSD"));
    }

    @Test
    @DisplayName("Test increment hits")
    void testIncrementHits() {
        cache.incrementHits();
        assertEquals(1, cache.getHits());
    }

    @Test
    @DisplayName("Test increment misses")
    void testIncrementMisses() {
        cache.incrementMisses();
        assertEquals(1, cache.getMisses());
    }

    @Test
    @DisplayName("Test increment requests")
    void testIncrementRequests() {
        cache.incrementRequests();
        assertEquals(1, cache.getRequests());
    }

    @Test
    @DisplayName("Test get hits")
    void testGetHits() {
        assertEquals(0, cache.getHits());
    }

    @Test
    @DisplayName("Test get misses")
    void testGetMisses() {
        assertEquals(0, cache.getMisses());
    }

    @Test
    @DisplayName("Test get requests")
    void testGetRequests() {
        assertEquals(0, cache.getRequests());
    }

    @Test
    @DisplayName("Test get cache size, after adding 2 items")
    void testGetCacheSize() {
        cache.addToCache("EURUSD", 1.2);
        cache.addToCache("USDJPY", 110.0);
        assertEquals(2, cache.getCacheSize());
    }

    @Test
    @DisplayName("Test contains item")
    void testContainsItem() {
        cache.addToCache("EURUSD", 1.2);
        assertTrue(cache.containsItem("EURUSD"));
    }

    @Test
    @DisplayName("Test clear cache")
    void testClearCache() {
        cache.addToCache("EURUSD", 1.2);
        cache.addToCache("USDJPY", 110.0);
        cache.clearCache();
        assertEquals(0, cache.getCacheSize());
    }

    
    @Test
    @DisplayName("Test cache timer")
    void testCacheTimer() {
        cache.addToCache("EURUSD", 1.2);
        cache.cacheTimer("EURUSD", 100);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull(cache.getFromCache("EURUSD"));
    }
}
