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
public class PositionStackApiClient extends RestApiClient implements RestClient {

    private final String apiKey;

    public PositionStackApiClient(RestTemplate restTemplate,
                                  @Value("${positionstack.api.key}") String apiKey) {
        super(restTemplate);
        this.apiKey = apiKey;
    }

    public PositionStackApiResponse getLocationData(String searchLocation) {
        try {
            String url = getUrl(searchLocation);
            ResponseEntity<PositionStackApiResponse> response = executeRequestRepeated(url, 1, 5);
            return response.getStatusCode().is2xxSuccessful()
                    ? response.getBody()
                    : PositionStackApiResponse.builder().build();
        } catch (Exception e) {
            log.error("Could not fetch position due to error: {}", e.getMessage(), e);
            return PositionStackApiResponse.builder().build();
        }
    }

    public ResponseEntity<PositionStackApiResponse> executeRequest(String url) {
        ResponseEntity<PositionStackApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                PositionStackApiResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Successfully fetched data from uri= {}", url);
        }
        return response;
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
            }
            log.warn("Did not successfully connect to={}", url);
            throw new RuntimeException("Could not connect to=" + url + " HTTP fault code: " + response.getStatusCode().getReasonPhrase());
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
