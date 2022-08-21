package just.a.repo.project.mapper;

import just.a.repo.project.integration.model.openweathermap.OpenWeatherMapWeatherResponse;
import just.a.repo.project.model.Kelvin;
import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.model.WindDirection;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static just.a.repo.project.mapper.Utils.*;
import static just.a.repo.project.model.WindDirection.*;

public class WeatherModelMapper {

    public static WeatherModel map(OpenWeatherMapWeatherResponse response) {

        return WeatherModel.builder()
                .description(mapDescription(response)) //"Växlande molnighet"
                .feelsLikeTemperature(mapFeelsLikeTemperature(response))
                .humidity(mapHumidity(response))
                //.pressure(getPressure(response))
                .sunrise(mapSunRise(response))
                .sunset(mapSunset(response))
                .temperature(mapTemperature(response))
                //.weatherConditions(getWeatherConditions(response)) "Clouds"
                .windDirection(mapWindDirection(response))
                .windSpeed(mapWindSpeed(response))
                .build();
    }

    private static String mapDescription(OpenWeatherMapWeatherResponse response) {
        return response.getWeather().get(0).getDescription();
    }

    private static String mapFeelsLikeTemperature(OpenWeatherMapWeatherResponse response) {
        Kelvin feelsLike = Kelvin.builder()
                .temperature(response.getMain().getFeels_like())
                .build();

        return Math.round(feelsLike.toCelcius().getTemperature()) + CELCIUS_TEXT;
    }

    private static String mapHumidity(OpenWeatherMapWeatherResponse response) {
        return response.getMain().getHumidity() + HUMIDITY_TEXT;
    }

    private static double getPressure(OpenWeatherMapWeatherResponse response) {
        return response.getMain().getPressure();
    }

    private static String mapSunRise(OpenWeatherMapWeatherResponse response) {
        LocalDateTime sunriseTime = getLocalDateTime(response.getSys().getSunrise());
        return sunriseTime.format(getDateTimeFormatterHHmm());
    }

    private static DateTimeFormatter getDateTimeFormatterHHmm() {
        String timeColonPattern = "HH:mm";
        return DateTimeFormatter.ofPattern(timeColonPattern);
    }

    private static LocalDateTime getLocalDateTime(long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.of(ZoneId.SHORT_IDS.get("ECT")));
    }

    private static String mapSunset(OpenWeatherMapWeatherResponse response) {
        LocalDateTime sunsetTime = getLocalDateTime(response.getSys().getSunset());
        return sunsetTime.format(getDateTimeFormatterHHmm());
    }

    private static String mapTemperature(OpenWeatherMapWeatherResponse response) {
        Kelvin temperature = Kelvin.builder()
                .temperature(response.getMain().getTemp())
                .build();
        return Math.round(temperature.toCelcius().getTemperature()) + CELCIUS_TEXT;
    }

    private static String getWeatherConditions(OpenWeatherMapWeatherResponse response) {
        //Vi mottar svaret på engelska, t.ex. "Clouds".
        return response.getWeather().get(0).getMain();
    }

    private static String mapWindDirection(OpenWeatherMapWeatherResponse response) {
        double direction = response.getWind().getDeg();

        if (direction >= 0 && direction < 30) {
            return getText(direction, N);
        } else if (direction >= 30 && direction < 60) {
            return getText(direction, NE);
        } else if (direction >= 60 && direction < 120) {
            return getText(direction, E);
        } else if (direction >= 120 && direction < 150) {
            return getText(direction, SE);
        } else if (direction >= 150 && direction < 210) {
            return getText(direction, S);
        } else if (direction >= 210 && direction < 240) {
            return getText(direction, SW);
        } else if (direction >= 240 && direction < 300) {
            return getText(direction, W);
        } else if (direction >= 300 && direction < 330) {
            return getText(direction, NW);
        } else if (direction >= 330 && direction < 360) {
            return getText(direction, N);
        }
        return "";
    }

    private static String getText(double degrees, WindDirection windDirection) {
        return String.format("%s° %s", degrees, windDirection.getName());
    }

    private static String mapWindSpeed(OpenWeatherMapWeatherResponse response) {
        return response.getWind().getSpeed() + WIND_SPEED_TEXT;
    }
}
