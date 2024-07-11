package com.example.ratesplatform.shared.externalservices.currencyservice.dto;

import com.example.ratesplatform.shared.common.annotations.JacksonAutoDetection;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JacksonAutoDetection
public record Currency(
        String symbol,
        String code,
        int decimals
) {}