package com.example.ratesplatform.rates.dto.request;

import com.example.ratesplatform.shared.common.annotations.JacksonAutoDetection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Builder
@Jacksonized
@JacksonAutoDetection
@Schema(name = "CreateRateRequestDto",
        description = "Data necessary to create a new Rate")
public record CreateRateRequestDto(
        @NotNull Integer brandId,
        @NotNull Integer productId,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull @Positive Integer price,
        @NotNull String currencyCode
) {}