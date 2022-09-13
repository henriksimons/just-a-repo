package just.a.repo.project.service;

import just.a.repo.project.integration.OpenWeatherMapApiClient;
import just.a.repo.project.integration.PositionStackApiClient;
import just.a.repo.project.integration.model.openweathermap.OpenWeatherMapWeatherResponse;
import just.a.repo.project.integration.model.openweathermap.fivedayprognosis.OpenWeatherMapPrognosisResponse;
import just.a.repo.project.integration.model.positionstack.PositionStackApiResponse;
import just.a.repo.project.mapper.CoordinatesMapper;
import just.a.repo.project.mapper.PrognosisModelMapper;
import just.a.repo.project.mapper.WeatherEntityMapper;
import just.a.repo.project.mapper.WeatherModelMapper;
import just.a.repo.project.model.Coordinates;
import just.a.repo.project.model.prognosis.PrognosisModel;
import just.a.repo.project.model.weather.WeatherModelExtended;
import just.a.repo.project.model.mongodb.WeatherEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final OpenWeatherMapApiClient openWeatherMapApiClient;
    private final PositionStackApiClient positionStackApiClient;
    private final CacheService cacheService;


    @Override
    public Coordinates getLocationCoordinates(String location) {
        PositionStackApiResponse positionApiResponse = positionStackApiClient.getLocationData(location);
        if (positionApiResponse.getData() != null) {
            String logMessage = positionApiResponse.getData().isEmpty() ? location : positionApiResponse.getData().get(0).getLabel();
            log.info("Received location data for: {}", logMessage);
        }
        Coordinates searchCoordinates = CoordinatesMapper.map(positionApiResponse);
        log.info("Converted location to coordinates: {}", searchCoordinates);
        return searchCoordinates;
    }

    @Override
    public WeatherModelExtended getWeather(String location) {
        Coordinates coordinates = getLocationCoordinates(location);
        //Fetching this asynchronously to save time.
        // TODO: 2022-08-28 Implement a save of this somewhere...
        CompletableFuture<PrognosisModel> prognosis = getPrognosisAsync(coordinates, location);
        return getWeather(coordinates, location);
    }

    @Override
    @Async
    public CompletableFuture<PrognosisModel> getPrognosisAsync(Coordinates coordinates, String location) {
        return CompletableFuture.supplyAsync(() -> getPrognosis(coordinates, location));
    }

    @Override
    public PrognosisModel getPrognosis(String location) {
        Coordinates coordinates = getLocationCoordinates(location);
        return getPrognosis(coordinates, location);
    }

    private WeatherModelExtended getWeather(@NonNull Coordinates coordinates, String location) {
        if (coordinates.getLat() == null || coordinates.getLon() == null) {
            log.warn("Missing coordinate data. Aborting call!");
            return WeatherModelExtended.builder().build();
        }
        OpenWeatherMapWeatherResponse weatherApiResponse = openWeatherMapApiClient.getWeather(coordinates);
        if (!weatherApiResponse.getWeather().isEmpty()) {
            log.info("Received weather for : {}", location);
        }
        return WeatherModelMapper.map(weatherApiResponse);
    }

    private PrognosisModel getPrognosis(@NonNull Coordinates coordinates, String location) {
        if (coordinates.getLat() == null || coordinates.getLon() == null) {
            return PrognosisModel.builder().build();
        }
        OpenWeatherMapPrognosisResponse prognosisApiResponse = openWeatherMapApiClient.getPrognosis(coordinates);
        if (!prognosisApiResponse.getList().isEmpty()) {
            log.info("Received prognosis for : {}", location);
        }
        return PrognosisModelMapper.map(prognosisApiResponse);
    }

    @Override
    public ResponseObject<WeatherEntity> saveWeatherEntity(WeatherModelExtended weatherExtendedModel) {
        try {
            WeatherEntity entity = WeatherEntityMapper.map(weatherExtendedModel);
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
