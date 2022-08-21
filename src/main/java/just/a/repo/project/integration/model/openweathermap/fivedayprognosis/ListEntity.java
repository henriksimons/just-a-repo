package just.a.repo.project.integration.model.openweathermap.fivedayprognosis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import just.a.repo.project.integration.model.openweathermap.Main;
import just.a.repo.project.integration.model.openweathermap.Weather;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListEntity {

    private Main main;
    private List<Weather> weather;
    private String dt_txt;
}
