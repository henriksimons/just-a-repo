package just.a.repo.project.integration.model.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {
    private String country;
    private int id;
    private double message;
    private long sunrise; //Unix time
    private long sunset; //Unix time
    private int type;
}