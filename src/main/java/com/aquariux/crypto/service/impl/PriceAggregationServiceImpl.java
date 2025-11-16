package com.aquariux.crypto.service.impl;

import com.aquariux.crypto.dto.ExternalPriceDTO;
import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.entity.AggregatedPrice;
import com.aquariux.crypto.entity.PriceFeed;
import com.aquariux.crypto.entity.PriceSource;
import com.aquariux.crypto.entity.TradingPair;
import com.aquariux.crypto.repository.AggregatedPriceRepository;
import com.aquariux.crypto.repository.PriceFeedRepository;
import com.aquariux.crypto.repository.PriceSourceRepository;
import com.aquariux.crypto.repository.TradingPairRepository;
import com.aquariux.crypto.service.ExternalClientService;
import com.aquariux.crypto.service.PriceAggregationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class PriceAggregationServiceImpl implements PriceAggregationService {

    @Autowired
    private TradingPairRepository tradingPairRepo;

    @Autowired
    private PriceSourceRepository priceSourceRepo;

    @Autowired
    private PriceFeedRepository priceFeedRepo;

    @Autowired
    private AggregatedPriceRepository aggregatedPriceRepo;

    @Autowired
    private ExternalClientService externalClientService;

    @Transactional
    @Override
    public void aggregatePrices() {
        List<TradingPair> pairs = tradingPairRepo.findAll();
        List<PriceSource> sources = priceSourceRepo.findAll();

        for (TradingPair pair : pairs) {
            List<PriceFeed> feeds = new ArrayList<>();
            for (PriceSource source : sources) {
                try {
                    ExternalPriceDTO dto =
                            externalClientService.fetchPrice(source.getCode(), pair.getSymbol());

                    PriceFeed feed = PriceFeed.builder()
                            .tradingPair(pair)
                            .source(source)
                            .bid(dto.getBid())
                            .ask(dto.getAsk())
                            .fetchedAt(LocalDateTime.now())
                            .rawJson(dto.getRawJson())
                            .build();

                    feeds.add(feed);
                } catch (Exception ex) {
                    log.error("Failed fetching price from {}: {}", source.getCode(), ex.getMessage());
                }
            }

            // Save all raw feeds
            priceFeedRepo.saveAll(feeds);

            // Compute best bid & ask
            feeds.removeIf(f -> f.getBid() == null || f.getAsk() == null);
            if (feeds.isEmpty()) continue;

            PriceFeed bestBidFeed = feeds.stream()
                    .max(Comparator.comparing(PriceFeed::getBid))
                    .orElse(null);

            PriceFeed bestAskFeed = feeds.stream()
                    .min(Comparator.comparing(PriceFeed::getAsk))
                    .orElse(null);

            AggregatedPrice agg = AggregatedPrice.builder()
                    .tradingPair(pair)
                    .bestBid(bestBidFeed.getBid())
                    .bestBidSource(bestBidFeed.getSource())
                    .bestAsk(bestAskFeed.getAsk())
                    .bestAskSource(bestAskFeed.getSource())
                    .aggregatedAt(LocalDateTime.now())
                    .build();

            aggregatedPriceRepo.save(agg);
        }
    }

    @Override
    public ResponseDTO findLatestPrices() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<AggregatedPrice> data = aggregatedPriceRepo.findLatestPricesForAllPairs();
            responseDTO.setData(data);
        } catch (Exception e) {
            log.error("Error occurred when findLatestPricesForAllPairs: ", e);
            responseDTO.setMessage("Error occurred when findLatestPricesForAllPairs");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
}