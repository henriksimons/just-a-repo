package just.a.repo.project.integration.model.positionstack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    public Double latitude;
    public Double longitude;
    public String label;
    public String name;
    public String type;
    public String number;
    public String street;
    public String postal_code;
    public Integer confidence;
    public String region;
    public String region_code;
    public String administrative_area;
    public String neighbourhood;
    public String country;
    public String country_code;
    public String map_url;
}