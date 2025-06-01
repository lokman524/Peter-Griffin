package org.petergriffin.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
        // For more complex configuration:
        // return new RestTemplateBuilder()
        //     .setConnectTimeout(Duration.ofSeconds(3))
        //     .setReadTimeout(Duration.ofSeconds(5))
        //     .build();
    }

}
