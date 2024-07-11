package com.example.ratesplatform.rates;

import com.example.ratesplatform.rates.dto.request.CreateRateRequestDto;
import com.example.ratesplatform.rates.dto.response.ApplicableRateResponseDto;
import com.example.ratesplatform.rates.dto.response.CreateRateResponseDto;
import com.example.ratesplatform.rates.usecase.*;
import com.example.ratesplatform.shared.common.annotations.CommonApiResponses;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.example.ratesplatform.shared.common.utils.Constants.RATE_BASE_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@Builder
@Validated
@RestController
@RequestMapping(RATE_BASE_CONTROLLER_PATH)
@CommonApiResponses
@Tag(name = "Rates", description = "Available operations for rates domain.")
public class RateController {

    // Use cases
    private final CreateRateUseCase createRateUseCase;
    private final GetRateByIdUseCase getRateByIdUseCase;
    private final GetApplicableRateUseCase getApplicableRateUseCase;
    private final ModifyRatePriceUseCase modifyRatePriceUseCase;
    private final DeleteRateUseCase deleteRateUseCase;

    // CREATE rate
    @Operation(summary = "Create a new rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rate created successfully")
    })
    @PostMapping
    public Mono<ResponseEntity<SuccessfulResponseDto<CreateRateResponseDto>>> createRate(
            @Valid @RequestBody CreateRateRequestDto requestBody
    ) {
        return createRateUseCase
                .createRate(requestBody)
                .map(res -> ResponseEntity.status(CREATED).body(res))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // GET rate by ID
    @Operation(summary = "Get rate by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rate found"),
            @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<SuccessfulResponseDto<Rate>>> getRateById(
            @PathVariable Long id
    ) {
        return getRateByIdUseCase
                .getRate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // MODIFY rate PRICE
    @Operation(summary = "Modify rate price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rate price updated successfully"),
            @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    @PatchMapping("/{id}/price")
    public Mono<ResponseEntity<SuccessfulResponseDto<Integer>>> modifyRatePrice(
            final @PathVariable Long id,
            final @RequestParam @Positive Integer price)
    {
        return modifyRatePriceUseCase.modifyRatePrice(id, price)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    // DELETE rate by ID
    @Operation(summary = "Delete rate by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rate deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Rate not found")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<SuccessfulResponseDto<Void>>> deleteRate(
            @PathVariable Long id)
    {
        return deleteRateUseCase.deleteRate(id)
                .map(res -> ResponseEntity.status(NO_CONTENT).body(res))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    // GET rate BY DATE, PRODUCT_ID AND BRAND_ID
    @Operation(summary = "Get applicable rate by date, product ID, and brand ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Applicable rate found"),
            @ApiResponse(responseCode = "404", description = "Applicable rate not found")
    })
    @GetMapping("/applicable")
    public Mono<ResponseEntity<SuccessfulResponseDto<ApplicableRateResponseDto>>> getApplicableRate(
            @RequestParam @NotNull LocalDate date,
            @RequestParam @NotNull Integer productId,
            @RequestParam @NotNull Integer brandId
    ) {
        return getApplicableRateUseCase
                .getRate(date, productId, brandId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
