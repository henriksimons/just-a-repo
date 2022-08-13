package just.a.repo.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TemperatureUnit {

    protected final double KELVIN_COEFFICIENT = 273.15;

}
