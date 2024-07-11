package com.example.ratesplatform.rates.usecase.impl;

import com.example.ratesplatform.rates.usecase.ModifyRatePriceUseCase;
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
public final class ModifyRatePriceInteractor implements ModifyRatePriceUseCase {

    // Repositories
    private final IRateRepository rateRepository;

    // Mappers
    private final BaseMapper baseMapper;

    @Override
    public Mono<SuccessfulResponseDto<Integer>> modifyRatePrice(
            final Long id,
            final Integer price
    ) {
        log.info("START - modify rate price");

        log.debug("Updating rate with ID {} with new price {}", id, price);
        return rateRepository.updatePrice(id, price)
                .map(res -> {
                    log.debug("Rows updated in database: {}", res);

                    log.info("END - modify rate price");
                    return baseMapper.toModifyRatePriceSuccessfulResponse(res);
                })
                .switchIfEmpty(Mono.fromCallable(() -> {
                    log.warn("0 rows were modified. Rate not found");
                    return null;
                }));
    }
}
