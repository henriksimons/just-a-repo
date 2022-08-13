package just.a.repo.project.mapper;

import just.a.repo.project.integration.model.OpenWeatherMapApiResponse;
import just.a.repo.project.model.Kelvin;
import just.a.repo.project.model.WeatherModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class WeatherModelMapper {

    public static WeatherModel map(OpenWeatherMapApiResponse response) {

        return WeatherModel.builder()
                .description(getDescription(response))
                .feelsLikeTemperature(getFeelsLike(response))
                .humidity(getHumidity(response))
                .pressure(getPressure(response))
                .sunrise(getSunrise(response))
                .sunset(getSunset(response))
                .temperature(getTemperature(response))
                .weatherConditions(getWeatherConditions(response))
                .windDirection(getWindDirection(response))
                .windSpeed(getWindSpeed(response))
                .build();
    }

    private static String getDescription(OpenWeatherMapApiResponse response) {
        return response.getWeather().get(0).getDescription();
    }

    private static double getFeelsLike(OpenWeatherMapApiResponse response) {
        Kelvin feelsLike = Kelvin.builder()
                .temperature(response.getMain().getFeels_like())
                .build();
        return Math.round(feelsLike.toCelcius().getTemperature());
    }

    private static double getHumidity(OpenWeatherMapApiResponse response) {
        return response.getMain().getHumidity();
    }

    private static double getPressure(OpenWeatherMapApiResponse response) {
        return response.getMain().getPressure();
    }

    private static String getSunrise(OpenWeatherMapApiResponse response) {
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

    private static String getSunset(OpenWeatherMapApiResponse response) {
        LocalDateTime sunsetTime = getLocalDateTime(response.getSys().getSunset());
        return sunsetTime.format(getDateTimeFormatterHHmm());
    }

    private static double getTemperature(OpenWeatherMapApiResponse response) {
        Kelvin temperature = Kelvin.builder()
                .temperature(response.getMain().getTemp())
                .build();
        return Math.round(temperature.toCelcius().getTemperature());
    }

    private static String getWeatherConditions(OpenWeatherMapApiResponse response) {
        return response.getWeather().get(0).getMain();
    }

    private static String getWindDirection(OpenWeatherMapApiResponse response) {
        int degrees = response.getWind().getDeg();

        // TODO: 2022-08-13 CONTINUE HERE!
        return null;

    }

    private static double getWindSpeed(OpenWeatherMapApiResponse response) {
        return response.getWind().getSpeed();
    }
}
