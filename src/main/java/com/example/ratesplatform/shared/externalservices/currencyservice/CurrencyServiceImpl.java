package com.example.ratesplatform.shared.externalservices.currencyservice;

import com.example.ratesplatform.shared.externalservices.currencyservice.dto.Currency;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyServiceImpl implements ICurrencyService{
    @Override
    public Flux<Currency> findAllCurrencies() {
        return null;
    }

    @Override
    public Mono<Currency> findCurrencyByCurrencyCode(String currencyCode) {
        return null;
    }
}
