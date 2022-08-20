package just.a.repo.project.service;

import just.a.repo.project.integration.OpenWeatherMapApiClient;
import just.a.repo.project.integration.PositionStackApiClient;
import just.a.repo.project.integration.model.openweathermap.OpenWeatherMapApiResponse;
import just.a.repo.project.integration.model.positionstack.PositionStackApiResponse;
import just.a.repo.project.mapper.CoordinatesMapper;
import just.a.repo.project.mapper.WeatherEntityMapper;
import just.a.repo.project.mapper.WeatherModelMapper;
import just.a.repo.project.model.Coordinates;
import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.mongodb.model.WeatherEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final OpenWeatherMapApiClient openWeatherMapApiClient;
    private final PositionStackApiClient positionStackApiClient;


    public Coordinates getLocationCoordinates(String location) {
        PositionStackApiResponse positionApiResponse = positionStackApiClient.getLocationData(location);
        if (positionApiResponse.getData() != null) {
            log.info("Received location data for: {}", location);
        }
        Coordinates searchCoordinates = CoordinatesMapper.map(positionApiResponse);
        log.info("Converted location to coordinates: {}", searchCoordinates);
        return searchCoordinates;
    }

    public WeatherModel getWeather(String location) {
        Coordinates coordinates = getLocationCoordinates(location);
        return getWeather(coordinates, location);
    }

    private WeatherModel getWeather(@NonNull Coordinates coordinates, String location) {
        if (coordinates.getLat() == null || coordinates.getLon() == null) {
            return WeatherModel.builder().build();
        }
        OpenWeatherMapApiResponse weatherApiResponse = openWeatherMapApiClient.getWeather(coordinates);
        if (!weatherApiResponse.getWeather().isEmpty()) {
            log.info("Received weather information for : {}", location);
        }
        return WeatherModelMapper.map(weatherApiResponse);
    }

    public ResponseObject<WeatherEntity> saveWeatherEntity(WeatherModel weatherModel) {
        try {
            WeatherEntity entity = WeatherEntityMapper.map(weatherModel);
            if (entity == null) {
                return createResponseObject(false, null);
            } else {
                weatherRepository.insert(entity);
                return createResponseObject(true, entity);
            }
        } catch (Exception e) {
            return createResponseObject(false, null);
        }
    }

    private ResponseObject<WeatherEntity> createResponseObject(@NonNull boolean successful, WeatherEntity content) {
        ResponseObject<WeatherEntity> response = new ResponseObject<>();
        response.setSuccessful(successful);
        response.setContent(content);
        return response;
    }
}
