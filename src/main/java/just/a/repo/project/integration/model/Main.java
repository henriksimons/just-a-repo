package just.a.repo.project.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    private double feels_like;
    private double humidity;
    private double pressure;
    private double temp;
    private double temp_max;
    private double temp_min;
}