package com.example.ratesplatform.rates.usecase.impl;

import com.example.ratesplatform.rates.usecase.DeleteRateUseCase;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.common.mappers.BaseMapper;
import com.example.ratesplatform.shared.config.error.ServiceException;
import com.example.ratesplatform.shared.externalservices.h2db.repositories.IRateRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Builder
public final class DeleteRateInteractor implements DeleteRateUseCase {

    // Repositories
    private final IRateRepository rateRepository;

    // Mappers
    private final BaseMapper baseMapper;

    @Override
    public Mono<SuccessfulResponseDto<Void>> deleteRate(final Long id) {
        log.info("START - delete rate");

        return rateRepository
                .findById(id)
                .flatMap(rate -> {
                    log.debug("Deleting rate with ID - {}", id);

                    rateRepository.deleteById(Math.toIntExact(id)).subscribe();

                    log.info("END - delete rate");
                    return Mono.just(baseMapper.toDeleteRateSuccessfulResponse());
                })
                .doOnError(err -> log.error("Error deleting rate {} - {}", id, err.getMessage()))
                .switchIfEmpty(Mono.error(new ServiceException(
                        String.valueOf(HttpStatus.NOT_FOUND.value()), "Not found")));
    }
}
