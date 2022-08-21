package just.a.repo.project.model.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherModel {

    protected String description;
    protected String feelsLikeTemperature;
    protected String humidity;
    protected String temperature;
}
