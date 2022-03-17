package org.aibles.failwall.user.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    private static final int OTP_LIFE_TIME = 3;

    @Bean
    public LoadingCache<String, String> createOtpCacheBean() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(OTP_LIFE_TIME, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key.toUpperCase();
                    }
                });
    }
}
