package just.a.repo.project.service;

import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private Cache<String, String> cache;

}
