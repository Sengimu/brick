package net.sengimu.brickback.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${server-info.loginRate}")
    private long loginRate;

    @Bean("loginRateCache")
    public Cache<String, Object> loginRateCache() {

        return Caffeine.newBuilder()
                .expireAfterWrite(loginRate, TimeUnit.SECONDS)
                .initialCapacity(20)
                .maximumSize(100)
                .build();
    }

    @Bean("tokenCache")
    public Cache<String, Object> tokenCache() {

        return Caffeine.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
    }

    @Bean("userHasTokenCache")
    public Cache<String, Object> userHasTokenCache() {

        return Caffeine.newBuilder()
                .expireAfterWrite(12, TimeUnit.HOURS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
    }

    @Bean("serverIdCache")
    public Cache<String, Object> serverIdCache() {

        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .initialCapacity(20)
                .maximumSize(100)
                .build();
    }
}
