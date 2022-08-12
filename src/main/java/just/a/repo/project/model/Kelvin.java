package just.a.repo.project.model;

import just.a.repo.project.model.Celcius;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Kelvin {

    private double temperature;

    public Celcius toCelcius() {
        return Celcius.builder()
                .temperature(this.temperature - 273.15)
                .build();
    }

}
