package com.example.ratesplatform.rates.usecase.impl;

import com.example.ratesplatform.rates.dto.response.ApplicableRateResponseDto;
import com.example.ratesplatform.rates.usecase.GetApplicableRateUseCase;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.common.mappers.BaseMapper;
import com.example.ratesplatform.shared.externalservices.currencyservice.ICurrencyService;
import com.example.ratesplatform.shared.externalservices.h2db.repositories.IRateRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Service
@Builder
public final class GetApplicableRateInteractor implements GetApplicableRateUseCase {

    // Repositories
    private final IRateRepository rateRepository;

    // Services
    private final ICurrencyService currencyService;

    // Mappers
    private final BaseMapper baseMapper;

    @Override
    public Mono<SuccessfulResponseDto<ApplicableRateResponseDto>> getRate(
            final LocalDate date,
            final Integer productId,
            final Integer brandId
    ) {
        log.info("START - GET rate by date, productId and brandId");

        return rateRepository
                .findByDateAndProductIdAndBrandId(date, productId, brandId)
                .flatMap(rate -> {
                    log.debug("Rate found - {}", rate);

                    log.debug("Retrieving currency data from currency service for rateId - {}", rate.getId());

                    return currencyService.findCurrencyByCurrencyCode(rate.getCurrencyCode())
                            .zipWith(Mono.just(rate));
                })
                .flatMap(tuple2 -> {
                    log.debug("Currency data retrieved - {}", tuple2.getT1());

                    log.debug("Parsing data to output format");
                    return Mono.just(baseMapper.toApplicableRate(tuple2.getT2(), tuple2.getT1()));
                })
                .map(applicableDate -> {
                    log.info("END - GET rate by date, productId and brandId");
                    return baseMapper.toGetApplicableRateSuccessfulResponse(applicableDate);
                });
    }
}
