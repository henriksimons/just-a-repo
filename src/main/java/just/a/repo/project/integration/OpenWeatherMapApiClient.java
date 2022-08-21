package just.a.repo.project.integration;

import just.a.repo.project.integration.model.openweathermap.OpenWeatherMapWeatherResponse;
import just.a.repo.project.integration.model.openweathermap.fivedayprognosis.OpenWeatherMapPrognosisResponse;
import just.a.repo.project.model.Coordinates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
public class OpenWeatherMapApiClient extends BaseApiClient {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public OpenWeatherMapApiClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public OpenWeatherMapWeatherResponse getWeather(Coordinates coordinates) {
        try {
            String url = getWeatherUrl(coordinates);
            ResponseEntity<OpenWeatherMapWeatherResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    OpenWeatherMapWeatherResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully fetched weather data from OpenWeatherMap API:\n{}", url);
                return response.getBody();
            } else
                return OpenWeatherMapWeatherResponse.builder().build();
        } catch (Exception e) {
            log.error("Could not fetch weather due to error: {}", e.getMessage(), e);
            return OpenWeatherMapWeatherResponse.builder().build();
        }
    }

    private String getWeatherUrl(Coordinates coordinates) {
        String lat = coordinates.getLat().toString();
        String lon = coordinates.getLon().toString();
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.openweathermap.org")
                .path("/data/2.5/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("lang", "SV")
                .build()
                .toString();
    }

    public OpenWeatherMapPrognosisResponse getPrognosis(Coordinates coordinates) {
        try {
            String url = getFiveDayWeatherUrl(coordinates);
            ResponseEntity<OpenWeatherMapPrognosisResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    OpenWeatherMapPrognosisResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully fetched weather data from OpenWeatherMap API:\n{}", url);
                return response.getBody();
            } else
                return OpenWeatherMapPrognosisResponse.builder().build();
        } catch (Exception e) {
            log.error("Could not fetch weather due to error: {}", e.getMessage(), e);
            return OpenWeatherMapPrognosisResponse.builder().build();
        }
    }

    private String getFiveDayWeatherUrl(Coordinates coordinates) {
        String lat = coordinates.getLat().toString();
        String lon = coordinates.getLon().toString();
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.openweathermap.org")
                .path("/data/2.5/forecast")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("lang", "SV")
                .queryParam("units", "metric")
                .build()
                .toString();
    }
}
