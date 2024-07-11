package com.example.ratesplatform.rates.usecase;

import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import reactor.core.publisher.Mono;

/**
 * Rate deletion functionality
 */
public interface DeleteRateUseCase {

    /**
     * Deletes an existing rate given an ID
     * @param id rate id
     * @return CreateRateResponseDto
     */
    Mono<SuccessfulResponseDto<Void>> deleteRate(final Long id);
}
