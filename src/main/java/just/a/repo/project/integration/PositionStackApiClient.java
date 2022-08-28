package just.a.repo.project.integration;

import just.a.repo.project.integration.model.positionstack.PositionStackApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
public class PositionStackApiClient extends BaseApiClient implements RestClient {

    @Value("${positionstack.api.key}")
    private String apiKey;


    public PositionStackApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public PositionStackApiResponse getLocationData(String searchLocation) {
        try {
            String url = getUrl(searchLocation);
            ResponseEntity<PositionStackApiResponse> response = executeRequestRepeated(url, 1, 5);
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

    public ResponseEntity<PositionStackApiResponse> executeRequest(String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                PositionStackApiResponse.class);
    }

    @Override
    public ResponseEntity<PositionStackApiResponse> executeRequestRepeated(String url, int attempt, int limit) {
        try {
            log.info("Attempt #{} /{}. Connecting to={} ", attempt, limit, url);
            return executeRequest(url);
        } catch (HttpClientErrorException response) {
            if (!response.getStatusCode().is2xxSuccessful() && attempt < 5) {
                attempt++;
                executeRequestRepeated(url, attempt, limit);
            } else {
                log.warn("Did not successfully connect to={}", url);
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.internalServerError().build();
        }
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
