package com.example.ratesplatform.shared.externalservices.h2db.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Value
@Builder
@JsonAutoDetect
@Table(name = "t_rates")
public class Rate {

    @Id
    Long id;

    Integer brandId;

    Integer productId;

    LocalDate startDate;

    LocalDate endDate;

    Integer price;

    String currencyCode;
}
