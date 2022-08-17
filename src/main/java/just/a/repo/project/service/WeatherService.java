package just.a.repo.project.service;

import just.a.repo.project.mapper.WeatherEntityMapper;
import just.a.repo.project.model.WeatherModel;
import just.a.repo.project.mongodb.model.WeatherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

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
