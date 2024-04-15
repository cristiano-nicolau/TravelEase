package hw.tqs.cache;

import java.util.HashMap;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;

@Component
public class Cache {

    private static Logger logger = LogManager.getLogger(Cache.class);
    private HashMap<String, Double> cacheMap = new HashMap<>();
    private int hits = 0;
    private int misses = 0;
    private int requests = 0;

    public Cache() {
    }

    public int getHits() {
        return hits;
    }

    public void incrementHits() {
        this.hits++;
    }

    public int getMisses() {
        return misses;
    }

    public void incrementMisses() {
        this.misses++;
    }

    public int getRequests() {
        return requests;
    }

    public void incrementRequests() {
        this.requests++;
    }

    public void addToCache(String key, Double value) {
        cacheMap.put(key, value);
    }

    public Double getFromCache(String key) {
        if (cacheMap.containsKey(key)) {
            incrementHits();
            incrementRequests();
            logger.info("Retrieved {} from cache: +1 hit +1 request", key);
            return cacheMap.get(key);
        }
        incrementMisses();
        incrementRequests();
        logger.info("{} not found in cache: +1 miss +1 request", key);
        return null;
    }

    public int getCacheSize() {
        return cacheMap.size();
    }

    public boolean containsItem(String key) {
        return cacheMap.containsKey(key);
    }

    public void clearCache() {
        cacheMap.clear();
    }

    public void cacheTimer(String key, int timeToLive){
        logger.info("Deleting item from cache in {}ms", timeToLive);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        cacheMap.remove(key);
                    }
                },
                timeToLive
        );
    }
}

