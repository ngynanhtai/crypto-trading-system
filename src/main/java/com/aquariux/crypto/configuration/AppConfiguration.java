package com.aquariux.crypto.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.aquariux.crypto")
@Getter
public class AppConfiguration {

    @Value("${fetch.aggregrated.price.binance-url}")
    private String fetchAggregationPriceBinanceUrl;

    @Value("${fetch.aggregrated.price.huobi-url}")
    private String fetchAggregationPriceHuobiUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
