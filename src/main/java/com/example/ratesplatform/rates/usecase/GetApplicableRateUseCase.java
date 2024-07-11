package com.example.ratesplatform.rates.usecase;


import com.example.ratesplatform.rates.dto.response.ApplicableRateResponseDto;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * This functionality consist of retrieving a Rate by filters
 */
public interface GetApplicableRateUseCase {

    /**
     * Retrieves a rate given a Date, product ID and brand ID
     * @param date rate date
     * @param productId rate product identifier
     * @param brandId rate brand identifier
     * @return successful response with rate data
     */
    Mono<SuccessfulResponseDto<ApplicableRateResponseDto>> getRate(
            final LocalDate date,
            final Integer productId,
            final Integer brandId);
}
