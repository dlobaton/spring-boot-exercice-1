package com.example.ratesplatform.rates.usecase.impl;

import com.example.ratesplatform.rates.dto.request.CreateRateRequestDto;
import com.example.ratesplatform.rates.dto.response.CreateRateResponseDto;
import com.example.ratesplatform.rates.usecase.CreateRateUseCase;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.common.mappers.BaseMapper;
import com.example.ratesplatform.shared.externalservices.h2db.repositories.IRateRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Builder
public final class CreateRateInteractor implements CreateRateUseCase {

    // Repositories
    private final IRateRepository rateRepository;

    // Mappers
    private final BaseMapper baseMapper;
    @Override
    public Mono<SuccessfulResponseDto<CreateRateResponseDto>> createRate(
            final CreateRateRequestDto requestBody
    ) {
        log.info("START - create new rate");

        log.debug("Saving new rate with input data - {}", requestBody);
        return rateRepository
                .save(baseMapper.toRateEntity(requestBody))
                .map(rate -> {
                    log.debug("Rate created successfully - {}", rate);

                    log.debug("Building output data");
                    return baseMapper.toCreateRateOutputData(rate);
                })
                .map(outputData -> {
                    log.info("END - create new rate");
                    return baseMapper.toCreateRateSuccessfulResponse(outputData);
                });
    }
}
