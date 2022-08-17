package just.a.repo.project.mapper;

import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.mongodb.model.WeatherEntity;

import java.time.Instant;

import static java.util.Objects.isNull;

public class WeatherEntityMapper {

    public static WeatherEntity map(WeatherModel model) {
        try {
            if (isNull(model)) {
                return null;
            }
            return WeatherEntity.builder()
                    .description(model.getDescription())
                    .feelsLikeTemperature(model.getFeelsLikeTemperature())
                    .humidity(model.getHumidity())
                    .id(Instant.now().toString())
                    .pressure(model.getPressure())
                    .sunrise(model.getSunrise())
                    .sunset(model.getSunset())
                    .temperature(model.getTemperature())
                    .weatherConditions(model.getWeatherConditions())
                    .windDirection(model.getWindDirection())
                    .windSpeed(model.getWindSpeed())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}