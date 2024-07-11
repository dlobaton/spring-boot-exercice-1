package com.example.ratesplatform.shared.externalservices.h2db.repositories;

import com.example.ratesplatform.shared.externalservices.h2db.model.Rate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface IRateRepository extends ReactiveCrudRepository<Rate, Integer> {

    /**
     * Method to find a rate given a Date, brandId and productId
     * @param date date on which the rate is found
     * @param productId rate product identifier
     * @param brandId rate brand identifier
     * @return Rate
     */
    @Query("SELECT * FROM t_rates WHERE brand_id = :brandId AND product_id = :productId " +
            "AND :date BETWEEN start_date AND end_date LIMIT 1")
    Mono<Rate> findByDateAndProductIdAndBrandId(
            final LocalDate date,
            final Integer productId,
            final Integer brandId);

    Mono<Rate> findById(final Long id);

    /**
     * Method to update rate price
     * @param id rate identifier
     * @param price new price
     * @return number of rows updated
     */
    @Modifying
    @Query("UPDATE t_rates SET price = :price WHERE id = :id")
    Mono<Integer> updatePrice(final Long id, final Integer price);

}
