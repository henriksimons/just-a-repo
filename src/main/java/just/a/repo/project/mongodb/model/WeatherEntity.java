package just.a.repo.project.mongodb.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document("weather")
public class WeatherEntity {

    private String description;
    private Double feelsLikeTemperature;
    private Double humidity;
    @Id
    private String id;
    private Double pressure;
    private String sunrise;
    private String sunset;
    private Double temperature;
    private String weatherConditions;
    private String windDirection;
    private Double windSpeed;

    public WeatherEntity(String description, Double feelsLikeTemperature, Double humidity, String id, Double pressure, String sunrise, String sunset, Double temperature, String weatherConditions, String windDirection, Double windSpeed) {
        super();
        this.description = description;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.humidity = humidity;
        this.id = id;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temperature = temperature;
        this.weatherConditions = weatherConditions;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "description='" + description + '\'' +
                ", feelsLikeTemperature=" + feelsLikeTemperature +
                ", humidity=" + humidity +
                ", id='" + id + '\'' +
                ", pressure=" + pressure +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", temperature=" + temperature +
                ", weatherConditions='" + weatherConditions + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", windSpeed=" + windSpeed +
                '}';
    }
}