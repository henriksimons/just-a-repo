package just.a.repo.project.controller;

import just.a.repo.project.integration.OpenWeatherMapApiClient;
import just.a.repo.project.mapper.WeatherModelMapper;
import just.a.repo.project.model.Coordinates;
import just.a.repo.project.model.SaveWeatherModelResponse;
import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.mongodb.model.WeatherEntity;
import just.a.repo.project.service.ResponseObject;
import just.a.repo.project.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestService {

    private final OpenWeatherMapApiClient openWeatherMapApiClient;
    private final WeatherService weatherService;

    @GetMapping("/coordinates")
    public ResponseEntity<Coordinates> getCoordinates(@RequestParam String location) {
        Coordinates coordinates = weatherService.getLocationCoordinates(location);
        return ResponseEntity.ok(coordinates);
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherModel> getWeather(@RequestParam String location) {
        try {
            return ResponseEntity.ok(weatherService.getWeather(location));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/weather")
    public ResponseEntity<SaveWeatherModelResponse> saveWeather(@RequestParam String location) {
        //Fetch weather
        WeatherModel weatherModel = weatherService.getWeather(location);
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
