package just.a.repo.project.integration;

import just.a.repo.project.integration.model.OpenWeatherMapApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OpenWeatherMapApiClient {

    private final RestTemplate restTemplate;
    @Value("${openweathermap.api.key}")
    private String apiKey;

    public OpenWeatherMapApiResponse getWeather() {
        try {
            String url = getUrl();
            ResponseEntity<OpenWeatherMapApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, OpenWeatherMapApiResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully fetched weather data from OpenWeatherMap API:\n{}", url);
                return response.getBody();
            } else return OpenWeatherMapApiResponse.builder().build();
        } catch (Exception e) {
            log.error("Could not fetch weather due to error: {}", e.getMessage(), e);
            return OpenWeatherMapApiResponse.builder().build();
        }
    }

    private String getUrl() {
        String lat = "59.308018";
        String lon = "18.076601";
        return UriComponentsBuilder.newInstance().scheme("http").host("api.openweathermap.org").path("/data/2.5/weather").queryParam("lat", lat).queryParam("lon", lon).queryParam("appid", apiKey).build().toString();
    }
}
