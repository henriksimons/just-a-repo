package just.a.repo.project.service;

import just.a.repo.project.model.Coordinates;
import just.a.repo.project.model.prognosis.PrognosisModel;
import just.a.repo.project.model.weather.WeatherModelExtended;
import just.a.repo.project.model.mongodb.WeatherEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {
    Coordinates getLocationCoordinates(String location);

    WeatherModelExtended getWeather(String location);

    @Async
    CompletableFuture<PrognosisModel> getPrognosisAsync(Coordinates coordinates, String location);

    PrognosisModel getPrognosis(String location);

    ResponseObject<WeatherEntity> saveWeatherEntity(WeatherModelExtended weatherExtendedModel);
}
