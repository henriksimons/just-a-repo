package just.a.repo.project.controller;

import just.a.repo.project.integration.OpenWeatherMapApiClient;
import just.a.repo.project.mapper.WeatherModelMapper;
import just.a.repo.project.model.WeatherModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestService {

    private final OpenWeatherMapApiClient openWeatherMapApiClient;

    @GetMapping("/weather")
    public ResponseEntity<WeatherModel> getWeather() {
        WeatherModel response = WeatherModelMapper.map(openWeatherMapApiClient.getWeather());
        return ResponseEntity.ok(response);
    }
}
