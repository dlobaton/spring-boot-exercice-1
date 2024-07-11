package com.example.ratesplatform.rates.usecase;


import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import reactor.core.publisher.Mono;

/**
 * This functionality consist of retrieving a Rate by ID
 */
public interface GetRateByIdUseCase {

    /**
     * Retrieves a rate given an existing ID
     * @param id rate id
     * @return CreateRateResponseDto
     */
    Mono<SuccessfulResponseDto<Rate>> getRate(final Long id);
}
