package just.a.repo.project.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Kelvin extends TemperatureUnit {

    private double temperature;

    public Celsius toCelcius() {
        return Celsius.builder()
                .temperature(this.temperature - KELVIN_COEFFICIENT)
                .build();
    }
}
