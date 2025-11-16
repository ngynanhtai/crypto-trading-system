package com.aquariux.crypto.service.impl;

import com.aquariux.crypto.configuration.AppConfiguration;
import com.aquariux.crypto.dto.ExternalPriceDTO;
import com.aquariux.crypto.service.ExternalClientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Slf4j
public class ExternalClientServiceImpl implements ExternalClientService {

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ExternalPriceDTO fetchPrice(String sourceCode, String tradingPair) {
        return switch (sourceCode) {
            case "HUOBI" -> fetchHuobi(tradingPair);
            case "BINANCE" -> fetchBinance(tradingPair);
            default -> null;
        };
    }

    private ExternalPriceDTO fetchBinance(String tradingPair) {
        try {
            ResponseEntity<String> resp = restTemplate.getForEntity(
                    appConfiguration.getFetchAggregationPriceBinanceUrl(), String.class);
            String rawJson = resp.getBody();

            // Binance returns an array of symbols
            ObjectMapper mapper = new ObjectMapper();
            JsonNode arrayNode = mapper.readTree(rawJson);

            for (JsonNode node : arrayNode) {
                if (node.get("symbol").asText().equalsIgnoreCase(tradingPair)) {
                    return ExternalPriceDTO.builder()
                            .symbol(tradingPair)
                            .bid(new BigDecimal(node.get("bidPrice").asText()))
                            .ask(new BigDecimal(node.get("askPrice").asText()))
                            .rawJson(node.toString())
                            .build();
                }
            }
        } catch (Exception ex) {
            log.error("Failed to fetch Binance price: {}", ex.getMessage());
        }
        return null;
    }

    private ExternalPriceDTO fetchHuobi(String tradingPair) {
        try {
            ResponseEntity<String> resp = restTemplate.getForEntity(
                    appConfiguration.getFetchAggregationPriceHuobiUrl(), String.class);
            String rawJson = resp.getBody();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rawJson);
            JsonNode tickers = root.get("data");

            if (tickers != null && tickers.isArray()) {
                for (JsonNode node : tickers) {
                    if (node.get("symbol").asText().equalsIgnoreCase(tradingPair)) {
                        return ExternalPriceDTO.builder()
                                .symbol(tradingPair)
                                .bid(new BigDecimal(node.get("bid").asText()))
                                .ask(new BigDecimal(node.get("ask").asText()))
                                .rawJson(node.toString())
                                .build();
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Failed to fetch Huobi price: {}", ex.getMessage());
        }
        return null;
    }
}