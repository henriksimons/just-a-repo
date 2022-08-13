package just.a.repo.project.model;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherModel {

    private String description;
    private Double feelsLikeTemperature;
    private Double humidity;
    private Double pressure;
    private String sunrise;
    private String sunset;
    private Double temperature;
    private String weatherConditions;
    private String windDirection;
    private Double windSpeed;

}
