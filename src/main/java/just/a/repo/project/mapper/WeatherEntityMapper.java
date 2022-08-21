package just.a.repo.project.mapper;

import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.mongodb.model.WeatherEntity;

import java.time.Instant;

import static java.util.Objects.isNull;
import static just.a.repo.project.mapper.Utils.*;

public class WeatherEntityMapper {

    public static WeatherEntity map(WeatherModel model) {
        try {
            if (isNull(model)) {
                return null;
            }
            return WeatherEntity.builder()
                    .description(model.getDescription())
                    .feelsLikeTemperature(toDouble(model.getFeelsLikeTemperature()))
                    .humidity(toDouble(model.getHumidity()))
                    .id(Instant.now().toString())
                    //.pressure(model.getPressure())
                    .sunrise(model.getSunrise())
                    .sunset(model.getSunset())
                    .temperature(toDouble(model.getTemperature()))
                    //.weatherConditions(model.getWeatherConditions())
                    .windDirection(model.getWindDirection())
                    .windSpeed(toDouble(model.getWindSpeed()))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    private static double toDouble(String string) {
        if (string != null) {
            String formattedString = "";
            if (string.contains(WIND_SPEED_TEXT) || string.contains(CELCIUS_TEXT) || string.contains(HUMIDITY_TEXT)) {
                //A-Z, a-z, °, %, /
                formattedString = string.replaceAll("[A-Za-z\\°\\%\\/]", "");
            }
            return Double.parseDouble(formattedString);
        }
        return 0D;
    }

}