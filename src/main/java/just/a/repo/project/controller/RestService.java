package just.a.repo.project.controller;

import just.a.repo.project.integration.OpenWeatherMapApiClient;
import just.a.repo.project.mapper.WeatherModelMapper;
import just.a.repo.project.model.SaveWeatherModelResponse;
import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.mongodb.model.WeatherEntity;
import just.a.repo.project.service.ResponseObject;
import just.a.repo.project.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestService {

    private final OpenWeatherMapApiClient openWeatherMapApiClient;
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherModel> getWeather() {
        WeatherModel response = WeatherModelMapper.map(openWeatherMapApiClient.getWeather());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/weather")
    public ResponseEntity<SaveWeatherModelResponse> saveWeather() {
        //Fetch weather
        WeatherModel weatherModel = WeatherModelMapper.map(openWeatherMapApiClient.getWeather());
        //Save weather to DB
        ResponseObject<WeatherEntity> response = weatherService.saveWeatherEntity(weatherModel);

        if (response.isSuccessful()) {
            return ResponseEntity.ok(SaveWeatherModelResponse.builder()
                    .successful(response.isSuccessful())
                    .savedEntity(response.getContent())
                    .build());
        } else
            return ResponseEntity.internalServerError().build();
    }
}
