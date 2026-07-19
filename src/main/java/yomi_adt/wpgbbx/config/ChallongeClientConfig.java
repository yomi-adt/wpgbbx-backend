package yomi_adt.wpgbbx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChallongeClientConfig {

    @Autowired
    private ChallongeProperties challongeProperties;

    @Bean
    public RestTemplate challongeRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Attach Challonge's required auth headers to every outgoing request.
        // Using an API v1 key against the v2.1 endpoints (Authorization-Type: v1)
        // is the simplest option for managing tournaments you own — no OAuth flow
        // needed.
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.set("Authorization", challongeProperties.getApiKey());
            headers.set("Authorization-Type", "v1");
            headers.set("Content-Type", "application/vnd.api+json");
            headers.set("Accept", "application/json");
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}