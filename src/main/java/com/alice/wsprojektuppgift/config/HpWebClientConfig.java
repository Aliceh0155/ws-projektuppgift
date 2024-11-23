package com.alice.wsprojektuppgift.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Component
@Configuration
public class HpWebClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
//   @Bean
//   public WebClient.Builder hpWebClientBuilder(){
//       return WebClient.builder();
//   }
}
