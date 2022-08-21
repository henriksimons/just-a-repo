package just.a.repo.project.model.weather;

import just.a.repo.project.mongodb.model.WeatherEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SaveWeatherResponse {

    private WeatherEntity savedEntity;
    private boolean successful;

}
