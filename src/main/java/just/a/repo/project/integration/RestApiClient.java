package just.a.repo.project.integration;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class RestApiClient {
    protected final RestTemplate restTemplate;

    public RestApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}

