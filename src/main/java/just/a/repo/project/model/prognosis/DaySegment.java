package just.a.repo.project.model.prognosis;

import just.a.repo.project.model.weather.WeatherModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DaySegment {

    private String time;
    private WeatherModel weatherModel;

}
