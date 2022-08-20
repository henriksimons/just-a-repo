package just.a.repo.project.integration;

import just.a.repo.project.integration.model.positionstack.Data;
import just.a.repo.project.integration.model.positionstack.PositionStackApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
public class PositionStackApiClient extends BaseApiClient {

    @Value("${positionstack.api.key}")
    private String apiKey;


    public PositionStackApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public PositionStackApiResponse getLocationData(String searchLocation) {
        try {
            String url = getUrl(searchLocation);
            ResponseEntity<PositionStackApiResponse> response = executeRequest(url);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully fetched weather data from PositionStack API:\n{}", url);
                return response.getBody();
            } else
                return PositionStackApiResponse.builder().build();
        } catch (Exception e) {
            log.error("Could not fetch position due to error: {}", e.getMessage(), e);
            return PositionStackApiResponse.builder().build();
        }
    }

    private ResponseEntity<PositionStackApiResponse> executeRequest(String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                PositionStackApiResponse.class);
    }

    private String getUrl(String searchLocation) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.positionstack.com/v1")
                .path("/forward")
                .queryParam("access_key", apiKey)
                .queryParam("query", searchLocation)
                .build()
                .toString();
    }
}