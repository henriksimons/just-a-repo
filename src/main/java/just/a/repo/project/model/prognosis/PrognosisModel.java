package just.a.repo.project.model.prognosis;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrognosisModel {

    /**
     * A five day weather prognosis. Each day is split into three hour segments.
     */

    private List<Day> days;

}
