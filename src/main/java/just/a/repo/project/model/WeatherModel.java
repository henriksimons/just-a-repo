package just.a.repo.project.model;

import lombok.*;

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
    private Integer sunrise;
    private Integer sunset;
    private Double temperature;
    private String weatherConditions;
    private Integer windDirection;
    private Double windSpeed;

}
