package just.a.repo.project.model.prognosis;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Day implements Comparable<Day> {

    private String date;
    private List<DaySegment> partsOfDay;

    @Override
    public int compareTo(Day d) {
        return this.date.compareTo(d.getDate());
    }

}
