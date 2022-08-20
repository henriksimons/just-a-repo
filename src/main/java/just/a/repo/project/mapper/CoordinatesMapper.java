package just.a.repo.project.mapper;

import just.a.repo.project.integration.model.positionstack.PositionStackApiResponse;
import just.a.repo.project.integration.model.positionstack.Result;
import just.a.repo.project.model.Coordinates;

public class CoordinatesMapper {

    public static Coordinates map(PositionStackApiResponse response) {
        if (response.getData() != null && !response.getData().isEmpty()) {

            Result locationData = response.getData().stream()
                    .findFirst()
                    .get();

            return Coordinates.builder()
                    .lat(locationData.getLatitude())
                    .lon(locationData.getLongitude())
                    .build();
        }
        return Coordinates.builder().build();
    }
}
