package io.github.easy4j.metrics.ehcache3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codahale.metrics.MetricRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Ehcache3Metrics Tests")
class Ehcache3MetricsTest {

    private MetricRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new MetricRegistry();
    }

    @Test
    @DisplayName("should create with registry and prefix")
    void shouldCreateWithRegistryAndPrefix() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThat(metrics.getRegistry()).isEqualTo(registry);
        assertThat(metrics.getPrefix()).isEqualTo("test.");
    }

    @Test
    @DisplayName("should create with empty prefix")
    void shouldCreateWithEmptyPrefix() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "");
        assertThat(metrics.getPrefix()).isEmpty();
    }

    @Test
    @DisplayName("should create with null prefix")
    void shouldCreateWithNullPrefix() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, null);
        assertThat(metrics.getPrefix()).isEmpty();
    }

    @Test
    @DisplayName("should throw when registry is null")
    void shouldThrowWhenRegistryIsNull() {
        assertThatThrownBy(() -> new Ehcache3Metrics(null, "test"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("should remove cache metrics")
    void shouldRemoveCacheMetrics() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        registry.register("test.ehcache.myCache.hit-count", (com.codahale.metrics.Gauge<Long>) () -> 0L);
        registry.register("test.ehcache.myCache.miss-count", (com.codahale.metrics.Gauge<Long>) () -> 0L);
        registry.register("test.ehcache.myCache.hit-rate", (com.codahale.metrics.Gauge<Double>) () -> 0.0);
        registry.register("test.ehcache.myCache.eviction-count", (com.codahale.metrics.Gauge<Long>) () -> 0L);
        registry.register("test.ehcache.myCache.size", (com.codahale.metrics.Gauge<Long>) () -> 0L);

        assertThat(registry.getNames()).contains(
                "test.ehcache.myCache.hit-count",
                "test.ehcache.myCache.miss-count"
        );

        metrics.removeCache("myCache");

        assertThat(registry.getNames()).doesNotContain(
                "test.ehcache.myCache.hit-count",
                "test.ehcache.myCache.miss-count"
        );
    }

    @Test
    @DisplayName("should throw when removing cache with null name")
    void shouldThrowWhenRemovingCacheWithNullName() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThatThrownBy(() -> metrics.removeCache(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("should get hit count returns zero by default")
    void shouldGetHitCountReturnsZeroByDefault() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThat(metrics.getHitCount(null)).isEqualTo(0L);
    }

    @Test
    @DisplayName("should get miss count returns zero by default")
    void shouldGetMissCountReturnsZeroByDefault() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThat(metrics.getMissCount(null)).isEqualTo(0L);
    }

    @Test
    @DisplayName("should get eviction count returns zero by default")
    void shouldGetEvictionCountReturnsZeroByDefault() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThat(metrics.getEvictionCount(null)).isEqualTo(0L);
    }

    @Test
    @DisplayName("should get cache size returns zero by default")
    void shouldGetCacheSizeReturnsZeroByDefault() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThat(metrics.getCacheSize(null)).isEqualTo(0L);
    }

    @Test
    @DisplayName("should return registry")
    void shouldReturnRegistry() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "test");
        assertThat(metrics.getRegistry()).isSameAs(registry);
    }

    @Test
    @DisplayName("should return prefix with dot suffix")
    void shouldReturnPrefixWithDotSuffix() {
        Ehcache3Metrics metrics = new Ehcache3Metrics(registry, "myapp");
        assertThat(metrics.getPrefix()).isEqualTo("myapp.");
    }
}
