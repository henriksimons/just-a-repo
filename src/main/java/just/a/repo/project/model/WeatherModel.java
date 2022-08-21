package just.a.repo.project.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherModel {

    private String description;
    private String feelsLikeTemperature;
    private String humidity;
    //private Double pressure; Tog bort eftersom orelevant.
    private String sunrise;
    private String sunset;
    private String temperature;
    //private String weatherConditions; Tog bort eftersom den ersätts av description på svenska.
    private String windDirection;
    private String windSpeed;

}
