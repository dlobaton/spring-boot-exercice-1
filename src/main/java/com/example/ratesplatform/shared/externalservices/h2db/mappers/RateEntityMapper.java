package com.example.ratesplatform.shared.externalservices.h2db.mappers;

import com.example.ratesplatform.rates.dto.request.CreateRateRequestDto;
import com.example.ratesplatform.rates.dto.response.ApplicableRateResponseDto;
import com.example.ratesplatform.rates.dto.response.CreateRateResponseDto;
import com.example.ratesplatform.shared.externalservices.currencyservice.dto.Currency;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import org.mapstruct.Mapper;

import java.util.Objects;
import java.util.function.Function;

@Mapper
public interface RateEntityMapper {

    /**
     * Method to parse CreateRateRequestDto to Rate entity
     * @param requestBody request body
     * @return Rate entity
     */
    Rate toRateEntity(final CreateRateRequestDto requestBody);

    default ApplicableRateResponseDto toApplicableRate(final Rate rate, final Currency currency){
        if(Objects.isNull(rate)){
            return null;
        }

        return ApplicableRateResponseDto.builder()
                .id(rate.getId())
                .brandId(rate.getBrandId())
                .productId(rate.getProductId())
                .startDate(rate.getStartDate())
                .endDate(rate.getEndDate())
                .currencyCode(rate.getCurrencyCode())
                .price(rate.getPrice()/ Math.pow(10, currency.decimals())) // price formatted
                .symbol(currency.symbol()) // symbol from currency service
                .build();
    }
    /**
     * Method to parse Rate entity to CreateRateResponseDto
     * @param rateEntity rate entity
     * @return CreateRateResponseDto
     */
    CreateRateResponseDto toCreateRateOutputData(final Rate rateEntity);
}
