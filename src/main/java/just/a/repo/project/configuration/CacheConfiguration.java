package just.a.repo.project.configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    @Bean
    // TODO: 2022-08-21 Uhh, figure this out...
    public Cache<String, String> cache() {
        Cache<String, String> cache = CacheBuilder
                .newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });
        return cache;
    }

}
