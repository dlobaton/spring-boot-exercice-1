package com.example.ratesplatform.shared.common.mappers;

import com.example.ratesplatform.shared.externalservices.h2db.mappers.RateEntityMapper;
import org.mapstruct.Mapper;

@Mapper
public interface BaseMapper extends SuccessfulResponseMapper, RateEntityMapper {
}
