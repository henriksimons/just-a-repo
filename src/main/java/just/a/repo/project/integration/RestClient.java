package just.a.repo.project.integration;

import org.springframework.http.ResponseEntity;

public interface RestClient<T> {
    ResponseEntity<T> executeRequest(String url);

    ResponseEntity<T> executeRequestRepeated(String url, int attempt, int limit);
}
