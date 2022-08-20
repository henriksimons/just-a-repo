package just.a.repo.project.integration;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class BaseApiClient {

    protected final RestTemplate restTemplate;

    public BaseApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}

