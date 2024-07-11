package com.example.ratesplatform.rates.usecase;


import com.example.ratesplatform.rates.dto.request.CreateRateRequestDto;
import com.example.ratesplatform.rates.dto.response.CreateRateResponseDto;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import reactor.core.publisher.Mono;

/**
 * Rate creation functionality
 */
public interface CreateRateUseCase {

    /**
     * Creates a new Rate
     * @param requestBody request body
     * @return CreateRateResponseDto
     */
    Mono<SuccessfulResponseDto<CreateRateResponseDto>> createRate(final CreateRateRequestDto requestBody);
}
