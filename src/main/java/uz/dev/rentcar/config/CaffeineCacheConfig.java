package uz.dev.rentcar.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by: asrorbek
 * DateTime: 7/16/25 22:38
 **/

@Configuration
public class CaffeineCacheConfig {

    public static final String CARS = "cars";

    public static final String AVAILABLE_CARS = "availableCars";

    public static final String CATEGORIES = "categories";

    public static final String OFFICES = "offices";

    @Bean
    public CacheManager cacheManager() {
        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(cacheType -> new CaffeineCache(cacheType.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(cacheType.getTtl(), TimeUnit.SECONDS)
                                .build()
                ))
                .collect(Collectors.toList());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @AllArgsConstructor
    @Getter
    public enum CacheType {

        CAR(CARS, 5 * 60),

        AVAILABLE_CAR(AVAILABLE_CARS, 10),

        CATEGORY(CATEGORIES, 5 * 60),

        OFFICE(OFFICES, 5 * 60);

        private final String cacheName;

        private final int ttl;

    }

}
