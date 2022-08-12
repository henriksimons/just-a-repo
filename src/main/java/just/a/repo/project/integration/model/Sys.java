package just.a.repo.project.integration.model;

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
    private int sunrise;
    private int sunset;
    private int type;
}