package io.github.easy4j.metrics.ehcache3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Ehcache3Metrics Tests")
class Ehcache3MetricsTest {
    private MetricRegistry registry;
    @BeforeEach void setUp() { registry = new MetricRegistry(); }

    @Test void shouldCreateWithRegistryAndPrefix() { var m = new Ehcache3Metrics(registry, "test"); assertThat(m.getRegistry()).isEqualTo(registry); assertThat(m.getPrefix()).isEqualTo("test."); }
    @Test void shouldCreateWithEmptyPrefix() { assertThat(new Ehcache3Metrics(registry, "").getPrefix()).isEmpty(); }
    @Test void shouldCreateWithNullPrefix() { assertThat(new Ehcache3Metrics(registry, null).getPrefix()).isEmpty(); }
    @Test void shouldThrowWhenRegistryIsNull() { assertThatThrownBy(() -> new Ehcache3Metrics(null, "test")).isInstanceOf(NullPointerException.class); }
    @Test void shouldRemoveCacheMetrics() {
        var m = new Ehcache3Metrics(registry, "test");
        registry.register("test.ehcache.c1.hit-count", (Gauge<Long>) () -> 0L);
        registry.register("test.ehcache.c1.miss-count", (Gauge<Long>) () -> 0L);
        assertThat(registry.getNames()).contains("test.ehcache.c1.hit-count");
        m.removeCache("c1");
        assertThat(registry.getNames()).doesNotContain("test.ehcache.c1.hit-count");
    }
    @Test void shouldThrowWhenRemovingCacheWithNullName() { assertThatThrownBy(() -> new Ehcache3Metrics(registry, "test").removeCache(null)).isInstanceOf(NullPointerException.class); }
    @Test void shouldGetHitCountReturnsZero() { assertThat(new Ehcache3Metrics(registry, "test").getHitCount(null)).isEqualTo(0L); }
    @Test void shouldGetMissCountReturnsZero() { assertThat(new Ehcache3Metrics(registry, "test").getMissCount(null)).isEqualTo(0L); }
    @Test void shouldGetEvictionCountReturnsZero() { assertThat(new Ehcache3Metrics(registry, "test").getEvictionCount(null)).isEqualTo(0L); }
    @Test void shouldGetCacheSizeReturnsZero() { assertThat(new Ehcache3Metrics(registry, "test").getCacheSize(null)).isEqualTo(0L); }
    @Test void shouldReturnRegistry() { assertThat(new Ehcache3Metrics(registry, "test").getRegistry()).isSameAs(registry); }
    @Test void shouldReturnPrefixWithDotSuffix() { assertThat(new Ehcache3Metrics(registry, "myapp").getPrefix()).isEqualTo("myapp."); }
}
