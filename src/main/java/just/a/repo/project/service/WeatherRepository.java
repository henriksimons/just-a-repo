package just.a.repo.project.service;

import just.a.repo.project.mongodb.model.WeatherEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface WeatherRepository extends MongoRepository<WeatherEntity, String> {

    @Query("{temperature:'?0'}")
    WeatherEntity findWeatherEntitiesByTemperature(double temperature);
}
