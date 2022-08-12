package just.a.repo.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Celcius {

    private double temperature;

    public Celcius toKelvin() {
        return Celcius.builder()
                .temperature(this.temperature + 273.15)
                .build();
    }

    public double getTemperature() {
        return temperature;
    }
}
