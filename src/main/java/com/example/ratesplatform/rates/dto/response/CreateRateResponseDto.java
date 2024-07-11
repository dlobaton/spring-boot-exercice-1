package com.example.ratesplatform.rates.dto.response;

import com.example.ratesplatform.shared.common.annotations.JacksonAutoDetection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
@JacksonAutoDetection
@Schema(name = "CreateRateResponseDto",
        description = "Output data generated after creating a new Rate")
public record CreateRateResponseDto(
        Long id,
        Integer brandId,
        Integer productId,
        LocalDate startDate,
        LocalDate endDate,
        Integer price,
        String currencyCode
) {}