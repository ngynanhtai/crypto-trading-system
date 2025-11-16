package com.aquariux.crypto.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

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

    @Bean
    public ObjectMapper objectMapper() {
        // Custom BigDecimal serializer (2 decimal places)
        SimpleModule decimalFormatModule = new SimpleModule();
        decimalFormatModule.addSerializer(BigDecimal.class, new JsonSerializer<>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                if (value == null) {
                    gen.writeNull();
                    return;
                }

                // If BigDecimal == 0 -> force "0.00"
                if (value.compareTo(BigDecimal.ZERO) == 0) {
                    gen.writeString("0.00");
                    return;
                }

                // Otherwise keep original scale/value and avoid scientific notation
                gen.writeString(value.stripTrailingZeros().toPlainString());
            }
        });

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModules(new JavaTimeModule(), decimalFormatModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
