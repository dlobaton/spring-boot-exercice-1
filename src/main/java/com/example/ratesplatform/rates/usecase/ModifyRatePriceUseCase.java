package com.example.ratesplatform.rates.usecase;


import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import reactor.core.publisher.Mono;

/**
 * This functionality consist of modifying the price of a Rate
 */
public interface ModifyRatePriceUseCase {

    /**
     * Modifies the price of a rate
     * @param id    rate identifier
     * @param price new price
     * @return Rate data updated
     */
    Mono<SuccessfulResponseDto<Integer>> modifyRatePrice(final Long id, final Integer price);
}
