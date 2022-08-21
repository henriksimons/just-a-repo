package just.a.repo.project.model.prognosis;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Day {

    private String date;
    private List<DaySegment> partsOfDay;
}
