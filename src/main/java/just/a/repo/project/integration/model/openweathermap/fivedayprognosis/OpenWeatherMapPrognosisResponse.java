package just.a.repo.project.integration.model.openweathermap.fivedayprognosis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapPrognosisResponse {

    private List<ListEntity> list;

}
