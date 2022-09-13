package just.a.repo.project.rest;

import just.a.repo.project.integration.OpenWeatherMapApiClient;
import just.a.repo.project.model.Coordinates;
import just.a.repo.project.model.prognosis.PrognosisModel;
import just.a.repo.project.model.weather.SaveWeatherResponse;
import just.a.repo.project.model.weather.WeatherModelExtended;
import just.a.repo.project.model.mongodb.WeatherEntity;
import just.a.repo.project.service.ResponseObject;
import just.a.repo.project.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/prognosis")
    public ResponseEntity<PrognosisModel> getPrognosis(@RequestParam String location) {
        try {
            return ResponseEntity.ok(weatherService.getPrognosis(location));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherModelExtended> getWeather(@RequestParam String location) {
        try {
            return ResponseEntity.ok(weatherService.getWeather(location));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/weather")
    public ResponseEntity<SaveWeatherResponse> saveWeather(@RequestParam String location) {
        //Fetch weather
        WeatherModelExtended weatherExtendedModel = weatherService.getWeather(location);
        //Save weather to DB
        ResponseObject<WeatherEntity> response = weatherService.saveWeatherEntity(weatherExtendedModel);

        if (response.isSuccessful()) {
            return ResponseEntity.ok(SaveWeatherResponse.builder()
                    .successful(response.isSuccessful())
                    .savedEntity(response.getContent())
                    .build());
        } else
            return ResponseEntity.internalServerError().build();
    }


}
