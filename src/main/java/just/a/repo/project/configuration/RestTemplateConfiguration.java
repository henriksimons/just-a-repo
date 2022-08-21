package just.a.repo.project.configuration;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }


    @Bean
    // TODO: 2022-08-21 Uhh, figure this out...
    public Cache<String, String> cache(){
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
