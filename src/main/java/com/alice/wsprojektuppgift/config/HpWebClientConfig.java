package com.alice.wsprojektuppgift.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HpWebClientConfig {

   @Bean
   public WebClient.Builder hpWebClientBuilder(){
       return WebClient.builder();
   }
}
