package just.a.repo.project.integration.model.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapWeatherResponse {
    private String base;
    private Clouds clouds;
    private int cod;
    private Coord coord;
    private int dt;
    private int id;
    private Main main;
    private String name;
    private Sys sys;
    private int timezone;
    private int visibility;
    private List<Weather> weather;
    private Wind wind;
}