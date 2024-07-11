package com.example.ratesplatform.rates.usecase.impl;

import com.example.ratesplatform.rates.usecase.GetRateByIdUseCase;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.common.mappers.BaseMapper;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import com.example.ratesplatform.shared.externalservices.h2db.repositories.IRateRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Builder
public final class GetRateByIdInteractor implements GetRateByIdUseCase {

    // Repositories
    private final IRateRepository rateRepository;

    // Mappers
    private final BaseMapper baseMapper;

    @Override
    public Mono<SuccessfulResponseDto<Rate>> getRate(final Long id
    ) {
        log.info("START - get rate by ID");

        log.debug("Retrieving rate with ID - {}", id);
        return rateRepository
                .findById(id)
                .map(res -> {
                    log.debug("Rate retrieved successfully - {}", res);

                    log.info("END - get rate by ID");
                    return baseMapper.toGetRateSuccessfulResponse(res);
                });
    }
}
