package com.example.ratesplatform.rates.dto.response;

import com.example.ratesplatform.shared.common.annotations.JacksonAutoDetection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
@JacksonAutoDetection
@Schema(name = "ApplicableRateResponseDto",
        description = "Output data generated for get rate by date, productId and brandId functionality")
public record ApplicableRateResponseDto(
        Long id,
        Integer brandId,
        Integer productId,
        LocalDate startDate,
        LocalDate endDate,
        Double price,
        String currencyCode,
        String symbol
) {}