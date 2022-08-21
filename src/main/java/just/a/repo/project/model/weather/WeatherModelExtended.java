package just.a.repo.project.model.weather;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherModelExtended extends WeatherModel {

    //private Double pressure; Tog bort eftersom orelevant.
    private String sunrise;
    private String sunset;
    //private String weatherConditions; Tog bort eftersom den ersätts av description på svenska.
    private String windDirection;
    private String windSpeed;

}
