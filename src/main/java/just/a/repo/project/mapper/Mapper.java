package just.a.repo.project.mapper;

import just.a.repo.project.model.Kelvin;
import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.integration.model.OpenWeatherMapApiResponse;

public class Mapper {

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
        return feelsLike.toCelcius().getTemperature();
    }

    private static double getHumidity(OpenWeatherMapApiResponse response) {
        return response.getMain().getHumidity();
    }

    private static double getPressure(OpenWeatherMapApiResponse response) {
        return response.getMain().getPressure();
    }

    private static int getSunrise(OpenWeatherMapApiResponse response) {
        return response.getSys().getSunrise();
    }

    private static int getSunset(OpenWeatherMapApiResponse response) {
        return response.getSys().getSunset();
    }

    private static double getTemperature(OpenWeatherMapApiResponse response) {
        Kelvin temperature = Kelvin.builder()
                .temperature(response.getMain().getTemp())
                .build();
        return temperature.toCelcius().getTemperature();
    }

    private static String getWeatherConditions(OpenWeatherMapApiResponse response) {
        return response.getWeather().get(0).getMain();
    }

    private static int getWindDirection(OpenWeatherMapApiResponse response) {
        return response.getWind().getDeg();
    }

    private static double getWindSpeed(OpenWeatherMapApiResponse response) {
        return response.getWind().getSpeed();
    }
}
