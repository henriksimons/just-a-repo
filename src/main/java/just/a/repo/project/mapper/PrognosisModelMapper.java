package just.a.repo.project.mapper;

import just.a.repo.project.integration.model.openweathermap.OpenWeatherMapWeatherResponse;
import just.a.repo.project.integration.model.openweathermap.fivedayprognosis.ListEntity;
import just.a.repo.project.integration.model.openweathermap.fivedayprognosis.OpenWeatherMapPrognosisResponse;
import just.a.repo.project.model.Kelvin;
import just.a.repo.project.model.prognosis.Day;
import just.a.repo.project.model.prognosis.DaySegment;
import just.a.repo.project.model.prognosis.PrognosisModel;
import just.a.repo.project.model.weather.WeatherModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static just.a.repo.project.mapper.Utils.CELCIUS_TEXT;
import static just.a.repo.project.mapper.Utils.HUMIDITY_TEXT;

public class PrognosisModelMapper {

    public static PrognosisModel map(OpenWeatherMapPrognosisResponse response) {
        if (response.getList() != null) {

            Map<String, List<ListEntity>> entitiesGroupedByDay = groupWeatherEntitiesPerDay(response);
            List<Day> days = new ArrayList<>();

            entitiesGroupedByDay.forEach((date, entities) -> {
                List<DaySegment> daySegments = entities.stream()
                        .map(PrognosisModelMapper::mapDaySegment)
                        .collect(Collectors.toList());

                Day day = mapDay(date, daySegments);
                days.add(day);
            });

            return PrognosisModel.builder().days(days).build();
        }
        return PrognosisModel.builder().build();
    }

    private static Map<String, List<ListEntity>> groupWeatherEntitiesPerDay(OpenWeatherMapPrognosisResponse response) {
        Map<String, List<ListEntity>> entitiesGroupedByDay = new HashMap<>();

        response.getList()
                .forEach(listEntity -> {
                    String[] dateAndTime = listEntity.getDt_txt().split(" "); //Följer formatet: "dt_txt": "2022-08-21 12:00:00"
                    String date = dateAndTime[0];
                    addWeatherDataToDate(entitiesGroupedByDay, listEntity, date);
                });
        return entitiesGroupedByDay;
    }

    private static DaySegment mapDaySegment(ListEntity entity) {
        return DaySegment.builder()
                .time(mapTime(entity))
                .weatherModel(WeatherModel.builder()
                        .description(entity.getWeather().get(0).getDescription())
                        .temperature(mapTemperature(entity))
                        .feelsLikeTemperature(mapFeelsLikeTemperature(entity))
                        .humidity(mapHumidity(entity))
                        .build())
                .build();
    }

    private static Day mapDay(String date, List<DaySegment> mappedSegments) {
        return Day.builder()
                .date(date)
                .partsOfDay(mappedSegments)
                .build();
    }

    private static String mapTime(ListEntity segment) {
        if (segment.getDt_txt().isBlank()) {
            return "n/a";
        } else {
            String[] dateAndTime = segment.getDt_txt().split(" ");
            String[] timeBlock = dateAndTime[1].split(":");
            return String.format("%s:%s", timeBlock[0], timeBlock[1]);
        }
    }

    private static void addWeatherDataToDate(Map<String, List<ListEntity>> map, ListEntity listEntity, String date) {
        if (map.containsKey(date)) {
            map.get(date).add(listEntity);
        } else {
            List<ListEntity> list = new ArrayList<>();
            list.add(listEntity);
            map.put(date, list);
        }
    }

    // TODO: 2022-08-21 Generify!
    private static String mapFeelsLikeTemperature(ListEntity entity) {
        Kelvin feelsLike = Kelvin.builder()
                .temperature(entity.getMain().getFeels_like())
                .build();

        return Math.round(feelsLike.toCelcius().getTemperature()) + CELCIUS_TEXT;
    }

    private static String mapTemperature(ListEntity entity) {
        Kelvin temperature = Kelvin.builder()
                .temperature(entity.getMain().getTemp())
                .build();
        return Math.round(temperature.toCelcius().getTemperature()) + CELCIUS_TEXT;
    }

    private static String mapHumidity(ListEntity entity) {
        return entity.getMain().getHumidity() + HUMIDITY_TEXT;
    }
}
