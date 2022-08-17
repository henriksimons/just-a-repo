package just.a.repo.project.service;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject<T> {

    private T content;
    private boolean successful;

}
