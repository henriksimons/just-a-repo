package just.a.repo.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Celsius extends TemperatureUnit{
    private double temperature;

    public Kelvin toKelvin() {
        return Kelvin.builder()
                .temperature(this.temperature + KELVIN_COEFFICIENT)
                .build();
    }
}
