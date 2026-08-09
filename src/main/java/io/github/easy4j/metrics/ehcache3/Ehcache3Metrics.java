package io.github.easy4j.metrics.ehcache3;

import java.util.Objects;

import org.ehcache.Cache;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;

/**
 * Registers Ehcache 3.x cache statistics with Dropwizard Metrics.
 * Provides gauges for hit/miss ratios, eviction counts, and other cache metrics.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
public class Ehcache3Metrics {

    private final MetricRegistry registry;
    private final String prefix;

    /**
     * Creates a new Ehcache3Metrics with the given registry and prefix.
     *
     * @param registry the Dropwizard MetricRegistry
     * @param prefix   the metric name prefix
     */
    public Ehcache3Metrics(MetricRegistry registry, String prefix) {
        this.registry = Objects.requireNonNull(registry, "MetricRegistry must not be null");
        this.prefix = (prefix != null && !prefix.isEmpty()) ? prefix + "." : "";
    }

    /**
     * Registers all standard metrics for the given cache.
     *
     * @param cacheName the cache name to use in metric names
     * @param cache     the Ehcache cache instance
     * @param <K>       the cache key type
     * @param <V>       the cache value type
     */
    public <K, V> void registerCache(String cacheName, Cache<K, V> cache) {
        Objects.requireNonNull(cacheName, "Cache name must not be null");
        Objects.requireNonNull(cache, "Cache must not be null");
        String metricName = prefix + "ehcache." + cacheName;

        registry.register(metricName + ".hit-count", (Gauge<Long>) () -> {
            try { return getHitCount(cache); } catch (Exception e) { return 0L; }
        });
        registry.register(metricName + ".miss-count", (Gauge<Long>) () -> {
            try { return getMissCount(cache); } catch (Exception e) { return 0L; }
        });
        registry.register(metricName + ".hit-rate", new RatioGauge() {
            @Override protected Ratio getRatio() {
                try {
                    long hits = getHitCount(cache), misses = getMissCount(cache);
                    return Ratio.of(hits, hits + misses);
                } catch (Exception e) { return Ratio.of(0, 0); }
            }
        });
        registry.register(metricName + ".eviction-count", (Gauge<Long>) () -> {
            try { return getEvictionCount(cache); } catch (Exception e) { return 0L; }
        });
        registry.register(metricName + ".size", (Gauge<Long>) () -> {
            try { return getCacheSize(cache); } catch (Exception e) { return 0L; }
        });
    }

    protected <K, V> long getHitCount(Cache<K, V> cache) { return 0L; }
    protected <K, V> long getMissCount(Cache<K, V> cache) { return 0L; }
    protected <K, V> long getEvictionCount(Cache<K, V> cache) { return 0L; }
    protected <K, V> long getCacheSize(Cache<K, V> cache) { return 0L; }

    public void removeCache(String cacheName) {
        Objects.requireNonNull(cacheName, "Cache name must not be null");
        String metricName = prefix + "ehcache." + cacheName;
        registry.remove(metricName + ".hit-count");
        registry.remove(metricName + ".miss-count");
        registry.remove(metricName + ".hit-rate");
        registry.remove(metricName + ".eviction-count");
        registry.remove(metricName + ".size");
    }

    public MetricRegistry getRegistry() { return registry; }
    public String getPrefix() { return prefix; }
}
