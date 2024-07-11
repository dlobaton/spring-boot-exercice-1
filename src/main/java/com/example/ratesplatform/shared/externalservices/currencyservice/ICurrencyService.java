package com.example.ratesplatform.shared.externalservices.currencyservice;

import com.example.ratesplatform.shared.externalservices.currencyservice.dto.Currency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICurrencyService {
    Flux<Currency> findAllCurrencies();
    Mono<Currency> findCurrencyByCurrencyCode(final String currencyCode);
}