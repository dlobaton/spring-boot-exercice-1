package com.example.ratesplatform.shared.common.mappers;

import com.example.ratesplatform.rates.dto.response.ApplicableRateResponseDto;
import com.example.ratesplatform.rates.dto.response.CreateRateResponseDto;
import com.example.ratesplatform.shared.common.dtos.SuccessfulResponseDto;
import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import org.mapstruct.Mapper;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

/**
 * Mapper class that will transform output data for available functionalities
 */
@Mapper
public interface SuccessfulResponseMapper {

    default SuccessfulResponseDto<CreateRateResponseDto> toCreateRateSuccessfulResponse(
            final CreateRateResponseDto createRateResponseDto) {

        if (Objects.isNull(createRateResponseDto)) {
            return null;
        }

        return SuccessfulResponseDto.<CreateRateResponseDto>builder()
                .message("Rate created successfully")
                .messageId(CREATED.toString())
                .data(createRateResponseDto)
                .build();
    }

    default SuccessfulResponseDto<Rate> toGetRateSuccessfulResponse(
            final Rate rate) {

        if (Objects.isNull(rate)) {
            return null;
        }

        return SuccessfulResponseDto.<Rate>builder()
                .message("Information retrieved successfully")
                .messageId(OK.toString())
                .data(rate)
                .build();
    }

    default SuccessfulResponseDto<ApplicableRateResponseDto> toGetApplicableRateSuccessfulResponse(
            final ApplicableRateResponseDto rate) {

        if (Objects.isNull(rate)) {
            return null;
        }

        return SuccessfulResponseDto.<ApplicableRateResponseDto>builder()
                .message("Information retrieved successfully")
                .messageId(OK.toString())
                .data(rate)
                .build();
    }


    default SuccessfulResponseDto<Integer> toModifyRatePriceSuccessfulResponse(
            final Integer res) {

        if (Objects.isNull(res) || res == 0) {
            return null;
        }

        return SuccessfulResponseDto.<Integer>builder()
                .message("Information retrieved successfully")
                .messageId(OK.toString())
                .data(res)
                .build();
    }

    default SuccessfulResponseDto<Void> toDeleteRateSuccessfulResponse() {

        return SuccessfulResponseDto.<Void>builder()
                .message("Deletion of Rate completed successfully")
                .messageId(NO_CONTENT.toString())
                .data(null)
                .build();
    }
}
